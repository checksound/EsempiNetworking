start "SERVER" java -classpath bin io.checksound.networking.multicast.MulticastServer

start "Client 1" java -classpath bin io.checksound.networking.multicast.MulticastClient localhost
start "Client 2" java -classpath bin io.checksound.networking.multicast.MulticastClient localhost
start "Client 3" java -classpath bin io.checksound.networking.multicast.MulticastClient localhost

