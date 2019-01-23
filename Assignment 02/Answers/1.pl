%
%		------------------------------INSTRUCTIONS TO EXECUTE THE PROGRAM-------------------------------------------------
%
%call issubset(string1,string2) to determine the output which is printed in the Console
%call l_increasing_s(in,out) where Inlist denotes input list and Outlist denotes output list and out is printed on the console
%
%

%
%		-----------------------------------PART-A-------------------------------------------------
%

% issubset(string1,string2) determines whether string1 is subset of string2
% issubsetlists(list11,list2) determines whether list1 is subset of list2
% checkmember(atom1,list1) checks if the atom1 is present in the list1 

% converts strings X and Y into lists L1 and L2 and then checks if L1 is subset of L2.
issubset(X,Y) :-string_codes(X,L1),string_codes(Y,L2),issubsetlists(L1,L2).

% empty list a subset of anyset
% if X is a member of L2 and L1 is also subset of L2 then [X,L1] is subset of L2.
issubsetlists([],_) :- !.
issubsetlists([X|L1],L2) :- checkmember(X,L2), issubsetlists(L1,L2).

% X is a member of the list [X,Y,Z....]  for example a is member of [a,b,c]
% if X is a member of sublist L then X is also member of list [Y,L].
checkmember(X,[X|_]).
checkmember(X,[_|L]) :- checkmember(X,L).


%
%		------------------------------------PART-B-------------------------------------------------
%


%call this method l_increasing_s(in,out) where Inlist denotes input list and Outlist denotes the final output list obtained
%to get output of longest increasing subsequence 
l_increasing_s(Inlist, Outlist) :-
	% we have used inbuilt aggregate operator aggregate(+Template, +Discriminator, :Goal, -Result)
	%get the length of list Innerlist in N	 
	aggregate(max(N,Innerlist), (wrapper_method(Inlist,Innerlist,[]), getlength(Innerlist, N)), max(_, Result)),
	% reverse the Result and store it in Outlist
	rev(Result, Outlist).
 
%accRev basically reverses the input list 
accRev([],A,A).
%append the first list in reverse way to get second list i.e always append head at front of second list
accRev([H|T],A,R):-  accRev(T,[H|A],R).

% rev is used to reverse L and Store in R
rev(L,R):-  accRev(L,[],R).

% Helper to get length of the list
getlength([],0).
getlength([_|L],N) :- getlength(L,N2), N is N2 + 1.

% for writing Text into the File  
write_to_file(File,Text):-
	 open(File,write,S),
	 write(S,Text),
	 nl,
	 close(S).

% method to find increasing subsequence
wrapper_method([],Currentlist,Currentlist). 
%we split the list into Head and Tail
wrapper_method([Head|Tail],Finallist,Currentlist) :-
	(Currentlist = [],wrapper_method(Tail,Finallist,[Head]));
	% we split the Currentlist into Head1 and then we compare element Head1 with element Head
	(Currentlist = [Head1|_],Head1 < Head,wrapper_method(Tail,Finallist,[Head | Currentlist]));
	%recursive method to loop again
	wrapper_method(Tail,Finallist,Currentlist).