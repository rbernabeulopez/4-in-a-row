import React from "react";
import $ from 'jquery';
import './front.css';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';
import {stompClient} from 'sockjs-client';

export const Front = () => {
    
    function setConnected(connected){
        $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
    }

    function connect() {
        var socket = new SockJS('http://localhost:8080/stomp-endpoint');
        console.log(Stomp.over(socket));
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            setConnected(true);
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/greetings', function (greeting) {
                showGreeting(JSON.parse(greeting.body));
            });
        });
    }

    function disconnect() {
        if (stompClient !== null) {
            stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }


    function sendName() {
        stompClient.send("/app/hello", {}, JSON.stringify({'name': $("#name").val()}));
    }
    
    function showGreeting(message) {
        $("#greetings").append("<tr><td>" + message.message + "</td></tr>");
    }

    $(function () {
        $("form").on('submit', function (e) {
            e.preventDefault();
        });
        $( "#connect" ).click(function() { connect(); });
        $( "#disconnect" ).click(function() { disconnect(); });
        $( "#send" ).click(function() { sendName(); });
    });

    return(
        <div id="main-content" >
        <div>
            <div>
                <form>
                    <div>
                        <label htmlFor="connect">WebSocket connection:</label>
                        <button id="connect"  type="submit">Connect</button>
                        <button id="disconnect" type="submit" disabled="disabled">Disconnect
                        </button>
                    </div>
                </form>
            </div>
            <div>
                <form>
                    <div>
                        <label htmlFor="name">What is your name?</label>
                        <input type="text" id="name" placeholder="Your name here..."/>
                    </div>
                    <button id="send" type="submit">Send</button>
                </form>
            </div>
        </div>
        <div>
            <div>
                <table id="conversation">
                    <thead>
                    <tr>
                        <th>Greetings</th>
                    </tr>
                    </thead>
                    <tbody id="greetings">
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    );
}