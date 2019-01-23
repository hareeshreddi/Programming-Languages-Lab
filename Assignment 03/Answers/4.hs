import Data.List
import Data.Ord
-- The base comditions for the dimentions of the different kinds of rooms
--for hall
hall = [(x,y) | x<-[15..20],y<-[10..15]]   
-- for bed rooms
bedroom = [(x,y) | x<-[10..15],y<-[10..15]]
-- for kitchen
kitchen = [(x,y) | x<-[7..15],y<-[5..13]]
-- for bathrooms
bathroom = [(x,y) | x<-[4..8],y<-[5..9]]
-- for garden
garden = [(x,y) | x<-[10..20],y<-[10..20]]
-- for the balcony
balcony = [(x,y) | x<-[5..10],y<-[5..10]]
-- the comparator function to decide which is the better configuration
-- while sorting the list of all possible combinations
cmp (a,_,_,_,_) (b,_,_,_,_) = compare a b       

-- the function to find all the combinations of the hall the bed rooms configurations
--  to decide which of the them is the best 
-- this function return all the  valid configurations
consumehb :: Int -> Int -> Int -> [(Int,Int,Int,Int,Int)] 
consumehb area be ha = [(be*a*b+ha*c*d,a,b,c,d) | (a,b) <- bedroom , (c,d) <- hall , be*a*b+ha*c*d < area]


-- out of all the configurations this function will give the best possible configuration by sorting all the possible configurations 
-- and finding the best among them that is the first element in the reversed list
combination :: Int -> Int -> Int -> [(Int,Int,Int,Int,Int)]
combination area be ha = 
    do
        let maxk = ceiling (fromIntegral be/3)
        let possiblehb = consumehb area be ha
        let sorted = sortBy cmp possiblehb
        return (head (reverse sorted)) 

-- the funciton that is called to get the final dimensions
-- of the rooms of bedrooms and hall 
design :: Int -> Int -> Int -> IO ()
design area bed_room hall = 
    do
    let all_combinations = combination area bed_room hall
    let (area,x1,y1,x2,y2) = head all_combinations
    putStrLn ("Bedroom: " ++ (show bed_room) ++ " (" ++ (show x1) ++ "x" ++ (show y1) ++ ") Hall: " ++ (show hall) ++ " (" ++ (show x2) ++ "x"++(show y2) ++" )")
