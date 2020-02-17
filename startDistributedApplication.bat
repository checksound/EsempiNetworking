rem (Listens on default port)
start "CLMandelbrotWorker1" java -classpath bin io.checksound.networking.CLMandelbrotWorker

rem (Listens on port 2501) 
start "CLMandelbrotWorker2" java -classpath bin io.checksound.networking.CLMandelbrotWorker 2501

rem (Listens on port 2502) 
start "CLMandelbrotWorker3" java -classpath bin io.checksound.networking.CLMandelbrotWorker 2502

rem (Listens on port 2503) 
start "CLMandelbrotWorker4" java -classpath bin io.checksound.networking.CLMandelbrotWorker 2503

rem start master
java -classpath bin io.checksound.networking.CLMandelbrotMaster localhost localhost:2501 localhost:2502 localhost:2503

