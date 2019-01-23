--main function to take inputs and print the Minimum number of operations required to equalize salaries of all workers
main = do
           line <- getLine
           --getting No.of Workers input
           let x = (readInt line) 
           line1 <- getLine
           --getting input of the salaries of workers
           let nums = map read (words line1) :: [Int]
           --call the wrapper function and print the final answer
           print (wrapper nums)

--Function to find the minimum no.of operations required to equalize salaries of all workers
--This uses 3 functions i.e. find sum of salaries, length of salaries and minimum salary among given salaries 
--To calcualte the final minimum no.of operations required 
wrapper :: [Int] -> Int
-- Logic to find the answer is sum of all salaries minus least salary * minimum salary
wrapper salaries_list = getSum salaries_list- ( (getLength salaries_list) * (getMin salaries_list))

--Function to convert string to integer
readInt :: String -> Int 
readInt = read  --library function to convert string to integer

--Function to get Sum of salaries of Workers
getSum :: [Int] -> Int
getSum salaries_list = sum salaries_list

--Function to get Number of Workers
getLength :: [Int] -> Int
getLength salaries_list = length salaries_list

--Function to get minimum salary among given salaries
getMin :: [Int] -> Int
getMin salaries_list = minimum salaries_list