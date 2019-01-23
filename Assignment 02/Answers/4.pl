
/*
	0th fib an 1th fib are the base cases
	then the fib(n) is calculaed by adding fib(n-1)+fib(n-2)
*/
fib(0,0).
fib(1,1).
fib(F,N) :-
    N>1,
    N1 is N-1,
    N2 is N-2,
    fib(F1,N1),
    fib(F2,N2),
    F is F1+F2.
/*
	0th lucas an 1th lucas are the base cases
	then the lucas(n) is calculaed by adding lucas(n-1)+lucas(n-2)
*/
lucas(2,0).
lucas(1,1).
lucas(L,N):-
	N>1,
	N1 is N-1,
	N2 is N-2,
	lucas(L1,N1),
	lucas(L2,N2),
	L is L1+L2.
/*
	0th tribonacci an 1th tribonacci and 2nd tribonacci are the base cases
	then the tribonacci(n) is calculaed by adding tribonacci(n-1)+tribonacci(n-2)+tribonacci(n-3).
*/
tri(0,0).
tri(1,1).
tri(1,2).
tri(T,N):-
	N>2,
	N1 is N-1,
	N2 is N-2,
	N3 is N-3,
	tri(T1,N1),
	tri(T2,N2),
	tri(T3,N3),
	T is T1+T2+T3.
/*
find the list up to n-1 index and then add the nth fibonacci number to the list
and then return the list.
*/
intNumSequence('Fibonacci',0,[0]).
intNumSequence('Fibonacci',1,[0,1]).
intNumSequence('Fibonacci',2,[0,1,1]).
intNumSequence('Fibonacci',N,L):-
    N1 is N-1,
    intNumSequence('Fibonacci',N1,L1),
    fib(F,N),
    append(L1,[F],L),
    write(L).
/*
find the list up to n-1 index and then add the nth lucas number to the list
and then return the list.
*/
intNumSequence('Lucas',0,[0]).
intNumSequence('Lucas',1,[2]).
intNumSequence('Lucas',2,[2,1]).
intNumSequence('Lucas',N,L):-
    N1 is N-1,
    intNumSequence('Lucas',N1,L1),
    lucas(F,N),
    append(L1,[F],L),
    write(L).
/*
find the list up to n-1 index and then add the nth tribonacci number to the list
and then return the list.
*/
intNumSequence('Tribonacci',0,[0]).
intNumSequence('Tribonacci',1,[0,1]).
intNumSequence('Tribonacci',2,[0,1,1]).
intNumSequence('Tribonacci',N,L):-
    N1 is N-1,
    intNumSequence('Tribonacci',N1,L1),
    tri(F,N),
    append(L1,[F],L),
    write(L).
/*
this function returns the nth lucas number
*/
nterm('Lucas',N,X):-
    var(X),nonvar(N),
	lucas(L,N),
	X is L.
/*
this function returns the nth fibonacci number
*/
nterm('Fibonacci',N,X) :-
     var(X),nonvar(N),
	fib(F,N),
	X is F.
/*
this function returns the nth tribonacci number
*/
nterm('Tribonacci',N,X) :-
     var(X),nonvar(N),
	tri(T,N),
	X is T.
/*
This function is used to check index of fibonacci number
*/
testFib(N,X1,F):-
    fib(F1,X1),
    X2 is X1+1,
    ( F = F1, N is X1; testFib(N,X2,F)).
/*
base cases for fibonacci index
*/
nterm('Fibonacci',1,1).
nterm('Fibonacci',2,1).
nterm('Fibonacci',3,2).
nterm('Fibonacci',N,X):-
    var(N),nonvar(X),
    testFib(N,1,X).
/*
This function is used to check index of lucas number
*/
testLuc(N,X1,F):-
    lucas(F1,X1),
    X2 is X1+1,
    ( F = F1, N is X1; testFib(N,X2,F)).
/*
base cases for lucas index
*/
nterm('Lucas',1,2).
nterm('Lucas',2,1).
nterm('Lucas',3,3).
nterm('Lucas',N,X):-
    var(N),nonvar(X),
    testLuc(N,1,X).
/*
This function is used to check index of tribonacci number
*/
testTri(N,X1,F):-
    tri(F1,X1),
    X2 is X1+1,
    ( F = F1, N is X1; testTri(N,X2,F)).
/*
base cases for tribonacci index
*/
nterm('Tribonacci',1,1).
nterm('Tribonacci',2,1).
nterm('Tribonacci',3,2).
nterm('Tribonacci',N,X):-
    var(N),nonvar(X),
    testTri(N,1,X).
