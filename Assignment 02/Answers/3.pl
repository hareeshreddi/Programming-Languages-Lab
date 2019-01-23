%
%		------------------------------INSTRUCTIONS TO EXECUTE THE PROGRAM-------------------------------------------------
%
%call read_from_file('sample_input.txt') from console to get output into 'encoded_output.txt'
%
%

% -------------------------------------------PROLOG CODE WITH APPROPRIATE COMMENTS------------------------------------------------------------------
% To read from input File 'sample_input.txt' and Encode the given input and write it to Output File 'encoded_output.txt'
read_from_file(File):-
	open(File,read,S),
	%get_char is a inbuilt function which obtains current character as Char from Stream S	
	get_char(S,Char),
	%this appends current stream inserts the elements of it into list X in Reverse order 
	current_stream1(Char,S,X),
	%reverse list X and store it in Y
	rev(X,Y),
	%Print the input list in Console
	write(Y),
	%list Y is enocded and the result is stored in EncodedList
	encodelist(Y,EncodedList),
	%print the output in Console
	write(EncodedList),
	%write into output file 
	write_list_to_file(EncodedList),
	close(S).

%The enocdelist method is used to encode the List 
encodelist([],[]).
encodelist([X|X1],[Z|Z1]) :- counter(X,1,Z,X1,Y1), encodelist(Y1,Z1).

%current_stream1 appends the stream to the passed list X from read_from_file 
current_stream1(end_of_file,_,_):-!.
current_stream1(Char,S,Ans):-
	%get_char is a inbuilt function which obtains current character as Char1 from Stream S
	get_char(S,Char1),
	current_stream1(Char1,S,Ans1),
	append(Ans1,[Char],Ans).

write_list_to_file(L):-
	open('encoded_output.txt',write,S),
	getlength(L,N),
	writer(S,L,N),
	close(S).

% function to write the list into stream S which is part of Output File.
writer(S,[X|Y],1):- write(S,X),nl,!.
writer(S,[X|Y],N):- N>1,N1 is N-1,write(S,X),write(S,','),writer(S,Y,N1).

%accRev basically reverses the input list
accRev([],A,A).
%append the first list in reverse way to get second list i.e always append head at front of second list
accRev([H|T],A,R):-  accRev(T,[H|A],R).

% rev is used to reverse L and Store in R
rev(L,R):-  accRev(L,[],R).

% Helper to get length of the list
getlength([],0).
getlength([X|L],N) :- getlength(L,N2), N is N2 + 1.

%this counter helps in counting similar consecutve characters and encode as we require
%when counter is 1 and no more content exists add A into result
counter(A,1,A,[],[]).
%when counter is more than 1 and no more content exists add [Length,A] into result
counter(A,Length,[Length,A],[],[]) :- Length > 1.
% A \= B is equivalent to not (A = B)
% when counter is 1 and previous element is not equal to current element add A into result
counter(A,1,A,[B|Y1],[B|Y1]) :- A \= B.
% when counter is more than 1 and previous element is not equal to current element add [Length,A] into result
counter(A,Length,[Length,A],[B|Y1],[B|Y1]) :- Length > 1, A \= B.
%increasing the counter value K1 when previous element is equal to current element
counter(A,Length,T,[A|X1],Y1) :- K1 is Length + 1, counter(A,K1,T,X1,Y1).