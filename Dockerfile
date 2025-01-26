FROM nginx:alpine
COPY --from=build /app/target/lottery.jar /lottery.jar
COPY nginx/api.conf /etc/nginx/conf.d/default.conf
EXPOSE 80 443
CMD ["nginx", "-g", "daemon off;"]