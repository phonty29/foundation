#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <netdb.h>
#include <sys/event.h>

#include "server.h"

Server *create_server(int port) { // Returns a pointer to a Server struct
    char port_str[10];
    sprintf(port_str, "%d", port);
    int sock;
    struct addrinfo hints, *res;
    memset(&hints, 0, sizeof(hints));
    hints.ai_family = AF_INET;      // IPv4
    hints.ai_socktype = SOCK_DGRAM; // UDP datagram
    hints.ai_flags = AI_PASSIVE;    // fill in my IP for me
    if (getaddrinfo(NULL, port_str, &hints, &res) != 0) {
        perror("getaddrinfo");
        exit(EXIT_FAILURE);
    }
    sock = socket(res->ai_family, res->ai_socktype, res->ai_protocol);
    if (sock == -1) {
        perror("socket");
        exit(EXIT_FAILURE);
    }
    if (bind(sock, res->ai_addr, res->ai_addrlen) == -1) {
        perror("bind");
        exit(EXIT_FAILURE);
    }
    freeaddrinfo(res);
    Server *server = malloc(sizeof(Server));
    memset(server, 0, sizeof(Server));
    server->port = port;
    server->socket_fd = sock;
    return server;
}

void on(Server *server, EventType event_type, void (*callback)(Request *)) { // Registers a callback for a specific event type
    server->callback_arr[event_type] = callback;
}

void start_server(Server *server) {
    int kq;
    struct kevent change_list;       // events we register
    struct kevent event_list[MAX_EVENTS]; // events we get
    int sock = server->socket_fd;

    // Create the kqueue
    kq = kqueue();
    if (kq == -1) {
        perror("kqueue");
        exit(EXIT_FAILURE);
    }

    // Register the socket for read events (similar to EPOLLIN)
    EV_SET(&change_list, sock, EVFILT_READ, EV_ADD | EV_ENABLE, 0, 0, NULL);
    if (kevent(kq, &change_list, 1, NULL, 0, NULL) == -1) {
        perror("kevent register");
        exit(EXIT_FAILURE);
    }

    printf("Listening on port %d\n", server->port);

    // The event loop
    while (1) {
        int nev = kevent(kq, NULL, 0, event_list, MAX_EVENTS, NULL);
        if (nev == -1) {
            perror("kevent wait");
            exit(EXIT_FAILURE);
        }

        for (int i = 0; i < nev; i++) {
            struct kevent *ev = &event_list[i];

            if (ev->filter == EVFILT_READ && (int)ev->ident == sock) {
                struct sockaddr client_addr;
                socklen_t addr_size = sizeof(client_addr);
                char buf[BUF_SIZE];
                memset(buf, 0, BUF_SIZE);

                int bytes_read = recvfrom(sock, buf, BUF_SIZE, 0, &client_addr, &addr_size);
                if (bytes_read == -1) {
                    perror("recvfrom");
                    continue; // don’t exit entire server on one recv failure
                }

                // Prepare request
                Request req = {client_addr, addr_size, sock, buf};

                // Run callback (like EventTypeMessage)
                server->callback_arr[EVENT_TYPE_MESSAGE](&req);
            }
        }
    }
}