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

    alert("foo");

    $("#upControl").bind("tap", function() {
        alert("move up");
        send("move up");
    });

    $("#downControl").bind("tap", function() {
        alert("move down");
        send("move left");
    });
    
    $("#leftControl").bind("tap", function() {
        alert("move left");
        send("move left");
    });
    
    $("#rightControl").bind("tap", function() {
        alert("move right");
        send("move right");
    });
    
}
