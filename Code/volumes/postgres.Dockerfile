FROM postgres:14.1-alpine

COPY init.sql /docker-entrypoint-initdb.d/create_tables.sql

ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD Qwerty!234
ENV POSTGRES_DB food_delivery

EXPOSE 5432