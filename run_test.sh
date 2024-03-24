#!/bin/bash

# Primeiro comando de teste
./gradlew app:testDebug --tests=com.example.oxeanbits_challenge.OnlineTest.when_i_stated_app_verify_weather_data &&
# Segundo comando de teste
./gradlew app:testDebug --tests=com.example.oxeanbits_challenge.OfflineTest.when_i_stated_app_verify_weather_data_no_network_connection

read -n 1 -s -r -p "Pressione qualquer tecla para sair"