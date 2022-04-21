FROM sbekberov/jre
ENV app_dir_app=/app
ENV config_dir=$app_dir_app/config
ENV application_dir=$app_dir_app/application
RUN mkdir $app_dir_app && chmod -R 777 $app_dir_app
RUN mkdir $config_dir && chmod -R 777 $config_dir
RUN mkdir $application_dir && chmod -R 777 $application_dir
COPY ./build/libs/*.jar $application_dir/
COPY ./src/main/resources/*.yaml $config_dir

WORKDIR $app_dir_app
VOLUME ["/mnt/app/сonfig", "/app/config"]
VOLUME ["/mnt/app/application", "/app/application"]
EXPOSE 8888/tcp
CMD java -jar $application_dir/trello-app-0.0.1-SNAPSHOT.jar --spring.config.location=file://$config_dir/application.yaml