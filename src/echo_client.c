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
    int sd;                   /* Socket Descriptor */
    int len;                  /* length */
    struct sockaddr_in caddr; /* client address */
    struct hostent *hp;
    char buff[BLEN];
    if ((sd = socket(AF_INET, SOCK_STREAM, 0)) < 0) //indirizzi IP con TCP, se UDP: SOCK_SDGRAM
    {
        printf("Error: can't create socket\n");
        exit(1);
    }
    caddr.sin_family = AF_INET;                       //domini IP
    caddr.sin_port = 5517;                            //porta del server a cui connetersi
    hp = gethostbyname("localhost");                  //rilevazione ind. IP del server ex.www.itisbusto.it
    bcopy(hp->h_addr, &caddr.sin_addr, hp->h_length); //caricamento ind. nella struttura di memoria da usare in connect
    if (connect(sd, (struct sockaddr *)&caddr, sizeof(caddr)) < 0)
    {
        printf("Errore nella connect!\n");
        exit(1);
    }
    printf("Inserisci le stringhe che vuoi inviare al server.\n(quit per terminare)\n");
    do
    {
        scanf("%s", buff);
        write(sd, buff, BLEN);
    } while (strcmp(buff, "quit")); //esci al quit

    read(sd, buff, BLEN);
    printf("Dal server: %s\n", buff);
    close(sd);
    exit(0);
}