FROM qnib/alpn-jre8
ADD some-text.txt /
ADD clj-kstream-lf-producer.jar /usr/share/clj-kstream/clj-kstream-lf-producer.jar
ENTRYPOINT ["java", "-jar", "/usr/share/clj-kstream/clj-kstream-lf-producer.jar"]
CMD []