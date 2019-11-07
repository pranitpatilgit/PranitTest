FROM airdock/oracle-jdk

RUN mkdir -p /PranitTest/lib
COPY src/main/resources/startup.sh /PranitTest/lib/
COPY target/PranitTest.jar /PranitTest/lib/

CMD ["/PranitTest/lib/startup.sh"]