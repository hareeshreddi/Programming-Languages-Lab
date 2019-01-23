--main function to take the inputs and then print the number of aloo parathas Chhotu can get by calling wrapper function
main = do
              putStr "Enter the value for x::"
              --Money Chhotu have
              value_x <- getLine
              let x = (readInt value_x)
              putStr "Enter the value for y::"
              --Cost of each aloo paratha
              value_y <- getLine
              let y = (readInt value_y)
              putStr "Enter the value for z::"
              --No.of tokens required to buy an aloo paratha
              value_z <- getLine
              let z = (readInt value_z)
              -- first buy aloo parathas using the money
              let ans1 = x `div` y
              --call the wrapper function to find additional aloo parathas Chhotu can get
              let ans2 = wrapper ans1 z
              -- Calaculate no.of aloo parathas Chhotu can get
              let ans = ans1 + ans2
              -- Convert the answer to String
              let ans3 = (readString ans)
              putStr "Chhotu can get "
              -- if ans2 is -1 it means no.of tokens required to buy one aloo paratha is 1
              if(ans2 == -1) then putStr "infinite"
              else putStr ans3
              putStrLn " aloo parathas"

-- wrapper function to calculate the no.of extra aloo parathas Chhotu can get
-- function signature
wrapper :: (Integral a) => a -> a -> a
-- recursive definition of wrapper function
wrapper c w = if(c==0 || c < w) then 0 -- base case when current no.of tokens is less than no.of tokens required to buy one aloo paratha
    else if(w==1) then -1 --base case if no.of tokens required to buy 1 aloo paratha is 1 return -1
    else 
    c `div` w + wrapper l w -- function call to find the no.of aloo parathas
    where l = c `div` w+ c `mod` w


-- Functions for converting Int to String and String to Int
-- function to convert string to integer
readInt :: String -> Int 
readInt = read  --library function to convert string to integer
-- function to convert integer to string
readString :: Int -> String 
readString = show