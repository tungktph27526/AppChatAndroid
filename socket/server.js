var express = require('express');
var app = express();
var http = require('http').Server(app);
var server = require('http').createServer();
var fs = require('fs');
var io = require("socket.io")(http);
var port = 3000;
var listUser = [];

io.sockets.on('connection', (socket) =>{
    console.log("connect...");
    // xu ly login
    socket.on('user_login', (user_name) =>{
        if(listUser.indexOf(user_name)>0){
            return;
        }
        listUser.push(user_name);
        socket.user = user_name;
        console.log(`xin chao ${user_name}`);
    });
    // xu ly tin nhan
    socket.on('send_message', (message) =>{
        io.sockets.emit('receiver_massage', {data: socket.user + ": " + message});
        console.log(`${socket.user} sent: ${message}`);
    });
});
http.listen(port, () => {
    console.log(`Socket.IO server running at http://localhost:${port}/`);
});
//console.log(`server is running at http://${hostname}:${port}`);