#include <stdio.h>
#include <stdlib.h>

#include "server.h"

void handle_message(Request *req) {
  if (sendto(req->server_sock_fd, req->message, BUF_SIZE, 0, &req->client_address, req->address_size) == -1) {
    perror("send");
    exit(EXIT_FAILURE);
  }
}

int main() {
  Server *s = create_server(41234);

  on(s, EVENT_TYPE_MESSAGE, handle_message);

  start_server(s);
}