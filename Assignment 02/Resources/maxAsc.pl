maxAscending(InList, OutList) :-
     processList(InList, [], OutList).


processList([X | Rest], State, OutList) :- 
     updateState(State, X, NewState),
     processList(Rest, NewState, OutList).

processList([], ListList, OutList) :- 
     last(RevOutList, ListList),
     reverse(RevOutList, OutList).

 /* General form:
      updateState(State, X, Carried, NewState)

      We either want to replace the first element of
      State with the list [X | Carried] (when  X is less
      than the head of that list, or recursively *keep* 
      the first element of State, and updateState the tail,
      using as new carry, the first element of State.

      An ordinary update starts with an empty carry.
 */

updateState(State, X, NewState) :- 
     updateState(State, X, [], NewState).

 /* If we run out of state, X was bigger than all the heads 
    and so we actually add a new element on the end.
 */

updateState([], X, Carried, [[X | Carried]]).

 /* Otherwise we either update at the current spot (when
    X is smaller than its head), or keep the current thing,
    and update the tail with a new carry.
 */

updateState([Y|Ys], X, Carried, NewState) :-
     Y = [H | _],
     X @< H,
     NewState = [[X | Carried] | Ys].

updateState([Y|Ys], X, _, NewState) :-
     Y = [H | _],
     X @>= H,
     updateState(Ys, X, Y, NewTail),
     NewState = [Y | NewTail].




% for writing Text into the File  
%write_to_file(File,Text):-
% open(File,write,S),
% writer(S,Text,),
% nl,
% close(S).