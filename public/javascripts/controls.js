$(document).ready(function() {
    console.log("I'm about to connect");
    connect();
    
    connectionReady();
    
});

function connectionReady() {
    if (ws.readyState != 1) {
        setTimeout(connectionReady, 500);
        console.log("round again");
    } else {
        pageReady();
    }
}

function connect() {
    ws = new WebSocket("ws://localhost:9000/echoSocket");

    ws.onopen = function(evt) { 
        console.log("connected");
    }

    ws.onclose = function(evt) {
        console.log("disconnected");
    }

    ws.onmessage = function(evt) {
        console.log("response: " + evt.data);
    }

    ws.onerror = function(evt) {
        console.log("error: " + evt.data);
    }
    
}

function disconnect() {
    ws.close();
}

function send(words) {
    ws.send(words);
}


// is this of any use?
// http://stackoverflow.com/questions/7919856/how-to-use-websockets-with-play-framework

function pageReady() {
    
    $("#leftControl").click(function() {
        send("move left");
    });
    
    $("#rightControl").click(function() {
        send("move right");
    });
    
}
