#!/bin/bash
rm /tmp/db2tm.jar
ant -f ant_db2tm.xml
rm -r /usr/lib/db2tm
rm -r /etc/db2tm
mkdir /etc/db2tm
mkdir /usr/lib/db2tm
cp /tmp/db2tm.jar /usr/lib/db2tm/
cp lib/** /usr/lib/db2tm
cp servers** /etc/db2tm