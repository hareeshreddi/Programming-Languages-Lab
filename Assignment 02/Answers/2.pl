
/*
married relation is between two people who are married
*/
marriedto(jatin,payal).
marriedto(pawan,sheetal).
marriedto(lucky,priya).
marriedto(suchi,amit).
marriedto(payal,jatin).
marriedto(sheetal,pawan).
marriedto(priya,lucky).
marriedto(amit,suchi).


%all the love relations
love(payal,pawan).
love(amit,payal).
love(lucky,payal).
love(jatin,priya).
love(suchi,pawan).
love(pawan,payal).
love(payal,amit).
love(payal,lucky).
love(priya,jatin).
love(pawan,suchi).
/*
for onrocks there are 4 conditions
x and y are married
both have lovers
they dont love each other
*/
onrocks(X,Y):-
    marriedto(X,Y),
    love(X,W),
    love(Y,V),
    not(love(X,Y)).
/*
for jealous there are two cases so i wrote two functions
one in which jealous is if one's spouse is loved by someone else
and 
one in which jealous is if one's lover is loved by someone else
*/
jealous(X):-
    love(X,W),
    love(Y,W),
    not(X=Y).

jealous(X):-
    married(X,Y),
    love(W,Y),
    not(X=W).
