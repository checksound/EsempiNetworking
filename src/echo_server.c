#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>

#define BLEN 1024
#define CODA 3

int main()
{
    int sd, sda;              /* Socket Descriptor, Socket Descriptor Accept */
    int len;                  /* length */
    struct sockaddr_in saddr; /* server address . struct socketaddr_in comprende: dominio (es. AF_INET, numero porta (2 byte), ind.
IP (4 byte) 8 byte non utilizzati)*/
    struct sockaddr caddr;    /* client address */
    char buff[BLEN] = {'\0'};
    if ((sd = socket(AF_INET, SOCK_STREAM, 0)) < 0) //indirizzi IP con TCP, se UDP: SOCK_SDGRAM
    {
        printf("Error: can't create socket\n");
        exit(1);
    }

    saddr.sin_family = AF_INET;                                 //domini IP
    saddr.sin_port = 5517;                                      //porta di accesso
    saddr.sin_addr.s_addr = INADDR_ANY;                         //qualsiasi ind. IP client accettato
    if (bind(sd, (struct sockaddr *)&saddr, sizeof(saddr)) < 0) //descrittore socket, puntatore al buffer in cui verrà copiato l'ind. del client chiamante, n.                                                            byte dell' ind. del client chiamante
    {
        printf("Error: can't bind\n");
        exit(1);
    }
    printf(".:: Server up & running ::.\n");
    listen(sd, CODA); /* max CODA connections = n. max. connession in sospeso*/
    printf("\toO Max %d connections allowed Oo\n", CODA);
    while (1)
    {
        /* wait for a connection */
        while ((sda = accept(sd, &caddr, &len)) < 0);        //ind. del client accettato e copiato in memoria
        if (!fork()) //il figlio eredita dal padre il descr. del socket e sarà rimosso solo alla terminazione del figlio
        {
            do
            {
                read(sda, buff, BLEN);
                printf("Client: %s\n", buff);
            } while (strcmp(buff, "quit")); //termina figlio al quit
            printf("Input terminato\n");
            write(sda, "Fatto", 10);
            close(sda);
            exit(0);
        }
        close(sda);
    }
}