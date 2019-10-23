vpath %.c ./src
vpath %.h ./includes

BUILD_DIR = ./build
CC=gcc

CCFLAGS ?=
LDFLAGS ?=

CFLAGS_DEBUG = -Wall -g -O0

all: echo_server echo_client

echo_server: echo_server.c
	$(CC) $^  $(CFLAGS_DEBUG) -o $(BUILD_DIR)/$@	

echo_client: echo_client.c
	$(CC) $^  $(CFLAGS_DEBUG) -o $(BUILD_DIR)/$@	

clean:
	rm -f $(BUILD_DIR)/echo_server $(BUILD_DIR)/echo_client

.PHONY: clean all
