#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <netdb.h>
#include <sys/event.h>

#include "server.h"

Server *create_server(int port) { // Returns a pointer to a Server struct
    char port_str[10];

    // sprintf() function allows you to create strings with specified formats, similar to printf(), 
    // but instead of printing to the standard output, it stores the resulting string in a character array provided by the user.
    sprintf(port_str, "%d", port);

    // Socket file descriptor
    int socket_fd;
    // hints is used to configure getaddrinfo, and res will receive the resulting address list
    struct addrinfo hints, *result_addr;

    // memset() function of type void accepts three variable as parameters that copies 
    // the character c (an unsigned char) to the first n characters of the string pointed to, by the argument str.

    // This function is used to fill a contiguous block of memory with a specific value. 
    // It also converts the value of a character to unsigned character and copies it into each of first n character of the object pointed by the given string. 
    // If the n is larger than string size, it will be undefined.

    // Here, it is used to initialize the hints structure to zero.
    memset(&hints, 0, sizeof(hints));
    // Setting up hints for getaddrinfo
    hints.ai_family = AF_INET;      // IPv4
    hints.ai_socktype = SOCK_DGRAM; // UDP datagram
    hints.ai_flags = AI_PASSIVE;    // fill in my IP for me
    // getaddrinfo() function returns one or more addrinfo structures
    // each of which contains an Internet address that can be specified in a call to the bind() function. 
    if (getaddrinfo(NULL, port_str, &hints, &result_addr) != 0) {
        perror("getaddrinfo");
        exit(EXIT_FAILURE); // EXIT_FAILURE = 1, 1 indicates unsuccessful termination
    }

    // socket() function creates an endpoint for communication and returns a file descriptor.
    socket_fd = socket(result_addr->ai_family, result_addr->ai_socktype, result_addr->ai_protocol);
    if (socket_fd == -1) {
        perror("socket");
        exit(EXIT_FAILURE); // EXIT_FAILURE = 1, 1 indicates unsuccessful termination
    }
    // bind() function binds a socket to an address structure.
    if (bind(socket_fd, result_addr->ai_addr, result_addr->ai_addrlen) == -1) {
        perror("bind");
        exit(EXIT_FAILURE); // EXIT_FAILURE = 1, 1 indicates unsuccessful termination
    }
    // Free the addrinfo structure allocated by getaddrinfo
    freeaddrinfo(result_addr);

    Server *server = malloc(sizeof(Server));

    // Initialize the Server struct with zeros
    memset(server, 0, sizeof(Server));
    server->port = port;
    server->socket_fd = socket_fd;

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