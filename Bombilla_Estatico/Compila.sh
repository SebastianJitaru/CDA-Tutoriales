#!/bin/bash

# Compilar interfase remoto (BombillaRMI.java)
echo "javac BombillaRMI.java"
javac BombillaRMI.java

# Compilar implementación interfase remoto (BombillaRMIServant.java)
echo "javac BombillaRMIServant.java" 
javac BombillaRMIServant.java 

# Generar skeleton y stubs para el servidor y el cliente (BombillaRMIServant_Stub.class Jdk<1.5 and BombillaRMIServant_Skeleton.class Jdk<1.2)
echo "rmic -vcompat BombillaRMIServant"
rmic -vcompat BombillaRMIServant

# Compilar Servidor (BombillaRMIServer.java)
echo "javac BombillaRMIServer.java"
javac BombillaRMIServer.java

# Compilar cliente (BombillaRMIClient.java)
echo "javac BombillaRMIClient.java"
javac BombillaRMIClient.java