# Build Angular app
FROM node:18 AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --prod

# Serve with Nginx
FROM nginx:alpine
COPY --from=builder /app/dist/learning-tracker /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
