var app = require("express")();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];

server.listen(8080, function(){
    console.log("Server is now running");
});

io.on('connection', function(socket){
    if(players.length==0){
    players.push(new player(socket.id, true));
    }
    else{
    players.push(new player(socket.id, false));
    }
    console.log("Player Connected!");
    socket.emit('socketID', {id: socket.id}, {bool : players[players.length -1].color});
    socket.broadcast.emit('newPlayer', { id: socket.id});
    socket.on('playerPlayed',function(data){
        data.id = socket.id;
        socket.broadcast.emit('playerPlayed', data);

    });
    socket.on('Disconnect', function(){
        console.log("Player Disconnected");
        for(var i = 0; i < players.length; i++){
            if(players[i].id == socket.id){
                players.splice(i, 1);
                console.log(players)
                socket.disconnect();
            }
        }
    });
    socket.on('disconnect', function(){
            console.log("Player Disconnected");
            for(var i = 0; i < players.length; i++){
                if(players[i].id == socket.id){
                    players.splice(i, 1);
                    console.log(players)
                    socket.disconnect();
                }
            }
        });
    players.push(new player(socket.id));

    if(players.length>1){
            sleep(300);
            console.log("Game will start");
            socket.broadcast.emit('twoPlayers');
            socket.emit('twoPlayers');
    }

});

function player(id, color){
    this.id= id;
    this.color = color;
}
function sleep(milliseconds) {
  const date = Date.now();
  let currentDate = null;
  do {
    currentDate = Date.now();
  } while (currentDate - date < milliseconds);
}