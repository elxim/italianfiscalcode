#!/bin/bash
# docker.rete.dom:5000/codicefiscale:v0.4-20171123
if [ -z $1 ]; then
        echo "impostare il nome dell'immagine ad es. docker.rete.dom:5000/codicefiscale:v0.4-20171123";
        exit;
else
        echo "avvio il container per l'immagine : $1 ";
        docker run -d -v ~/codicefiscale:/codicefiscale \
        --name codicefiscale -p 8085:8085 \
        --restart=unless-stopped \
        --link istatproxy:istatproxy \
        -e logging.file=/codicefiscale/logs/codicefiscale.log \
        -e server.contextPath=/cf \
        -e server.port=8085 \
        -e mp.codicefiscale.istatproxy-url=http://istatproxy:8084/istatproxy/comuni/%s \
        -e TZ=Europe/Rome \
        -t $1
fi;
