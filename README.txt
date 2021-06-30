on linux server, on amazon, you need to create directory basement in /home/admin/ ---/home/admin/basement and chmode 777 this directory

-----------------------------------
jars for reading excel
poi-5.0.0.jar
poi-ooxml-full-5.0.0.jar
poi-ooxml-5.0.0.jar
commons-compress-1.20.jar
xmlsbeans-4.0.0.jar

add for writing also
commons-collections4-4.1

------------------
jars for connection pooling

commons-dbcp.jar
commons-pool.jar
mysql-connector-java-5.1.28-bin.jar

------------------
On AWS Instance Reboot or Restart public DNS changes,
so you need to reconfigure some things , besides changing href in berishvili.eu5.org
first, you need to reconfigure Putty connection (reenter new public address and reenter password( .pem file)




