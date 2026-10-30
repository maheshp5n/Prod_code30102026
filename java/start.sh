jar -xvf Exicom-crm-0.0.1.jar
rm -rvf BOOT-INF/classes/static
rm -rvf BOOT-INF/classes/application.properties
rm -rvf META-INF
rm -rvf org
nohup java -Xms4G -Xloggc:gclog.txt -jar Exicom-crm-0.0.1.jar --spring.config.location=application.properties > log.txt &