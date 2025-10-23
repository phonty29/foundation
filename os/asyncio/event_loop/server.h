#ifndef _ASYNC_SERVER_H_ // if not defined _ASYNC_SERVER_H_ (to prevent multiple inclusions)
#define _ASYNC_SERVER_H_ // define the macro _ASYNC_SERVER_H_

#include <sys/socket.h>

#define BUF_SIZE 1024
#define MAX_EVENTS 10

typedef struct Request {
    struct sockaddr client_address;
    socklen_t address_size;
    // File descriptor for the server socket
    int server_sock_fd;
    char *message;
} Request;

typedef struct Server {
    int port;
    // File descriptor for the server socket
    int socket_fd;
    // Array of callback functions (function pointers) for different event types
    void (*callback_arr[1])(Request *);
} Server;

typedef enum EventType {
    EVENT_TYPE_MESSAGE
} EventType;

Server *create_server(int port);

void on (Server *server, EventType event_type, void (*callback)(Request *));

void start_server(Server *server);

#endif