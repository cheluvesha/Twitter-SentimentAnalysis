FROM 051c82900e82
WORKDIR /app
COPY . /app/
COPY DockerScript.sh .
RUN chmod +x /app/DockerScript.sh

ENV KAFKA_BROKER default
ENV KAFKA_TOPIC default
ENV TWEET_SAMPLE_FILE default
ENV MODEL default
ENV PATH2SAVE default
ENV AWSACCESSKEYID default
ENV AWSSECRETACCESSKEY default

RUN sbt update
RUN sbt clean package

ENTRYPOINT ["/app/DockerScript.sh"]