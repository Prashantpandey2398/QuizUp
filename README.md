# QuizUp

Introduction

Quiz Up is a live trivia game which anybody can play having smartphone. It is a real time game concept in which users race to get the right answers in 15 seconds. 10 general knowledge questions are thrown at the user one by one which have only one correct answer out of 3 options. One wrong answer and the user is eliminated! but after being eliminated the user can able to see further questions although he/she will not be able to give their responses.

Methodology

Client Side

The users of the game(clients) try to connect the remote server at the time of quiz through socket programming. Once the client opens the app, the app checks whether if it is the starting time of the quiz or not. Once the quiz starts all active clients try to connect to the server through Sockets. Server accepts that connection and starts sending Questions with their options and the time allocated for response (one by one). These questions hit all the clients at the same time and as soon as the questions arrives the time allotted for each response starts (15 seconds). Those users who responds correctly within those 15 seconds goes to the next question and the users who are not able to give their response within 15 seconds or who gives wrong answer are eliminated from the quiz. If the user is able to give all the responses correctly within 15 seconds then he/she will be declared as the winner. This was the working from the client side, now let’s see how the server works.

Server Side

Server receives the response given by different users at the same time through accept function of the ServerSocket class and creates a new thread for each client as a medium for interaction. After this the server creates a HashMap for storing the status of each client by storing the IP addresses of clients as key and their status as values. This status shows whether the client is eliminated or not. Once the client responds, the answer given by clients goes to the server and then server checks these two conditions: 
1. The answer given by client is matching the answer in the database or not?
2. The client responded within 15 seconds or not?

If any of this condition fails then the server changes the status of that particular client from active to inactive state.

1. If the client is active then the server will receive its responses and will give the feedback.
2. Once the client is inactive then the client will be able to see the further questions but he/she will not be able to respond.


Other Contributors:

Abhya Ranjan Yadav

Praman Satya
