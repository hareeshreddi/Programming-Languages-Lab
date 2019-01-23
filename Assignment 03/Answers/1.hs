import Data.Char(ord)

-- Defining the function parameters 
func_min :: String -> Int
--These are the base cases
--for null input or single char input the number of opeartions is 0 becaue they are already palindromes
func_min []  = 0
func_min [_] = 0
--Remove the first and last characters and find the absolute difference between them and call the function 
--recurively on the truncated string 
func_min input  = ( (func_min $ init $ tail input) + abs(subtract (ord (head input)) (ord (last input))))

--main function to get the answer
main = do
    name <- getLine  -- getting the input string
    let x = func_min name -- calling the function 
    print x --printing the result