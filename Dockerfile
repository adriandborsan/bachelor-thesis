FROM node:18 as builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build --configuration=production
CMD ["npm", "start"]

FROM nginx:1.23-alpine
COPY --from=builder app/dist/clientfront usr/share/nginx/html
