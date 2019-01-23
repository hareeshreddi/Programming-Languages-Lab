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


hall = [(x,y) | x<-[15..20],y<-[10..15]]                                                                             --calculating the cartesian product of possible sizes of rooms 
bedroom = [(x,y) | x<-[10..15],y<-[10..15]]
kitchen = [(x,y) | x<-[7..15],y<-[5..13]]
bathroom = [(x,y) | x<-[4..8],y<-[5..9]]
garden = [(x,y) | x<-[10..20],y<-[10..20]]
balcony = [(x,y) | x<-[5..10],y<-[5..10]]

cmp (a,_,_,_,_,_,_,_,_,_,_,_,_) (b,_,_,_,_,_,_,_,_,_,_,_,_) = compare a b                                --comparator function

consumehb :: Int -> Int -> Int -> [(Int,Int,Int,Int,Int)]                                                --possible ways of building halls and bed
consumehb ar be ha = [(be*a*b+ha*c*d,a,b,c,d) | (a,b) <- bedroom , (c,d) <- hall , be*a*b+ha*c*d < ar]

consumehbk :: [(Int,Int,Int,Int,Int)] -> Int -> Int -> [(Int,Int,Int,Int,Int,Int,Int)]                   --possible ways of building halls bed and kitchen
consumehbk input ar ki = [(x+ki*f*g,b,c,d,e,f,g) | (x,b,c,d,e)<-input , (f,g)<-kitchen , x+ki*f*g < ar]

consumehbkt :: [(Int,Int,Int,Int,Int,Int,Int)] -> Int -> Int -> [(Int,Int,Int,Int,Int,Int,Int,Int,Int)]  --possible ways of building halls bed kitchen and toilet
consumehbkt input ar be = [(x+(be+1)*g*h,a,b,c,d,e,f,g,h) | (x,a,b,c,d,e,f)<-input , (g,h)<-bathroom , x+(be+1)*g*h < ar]

consumehbktg :: [(Int,Int,Int,Int,Int,Int,Int,Int,Int)] -> Int -> [(Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int)]   --possible ways of building halls bed kitchen toilet garden
consumehbktg input ar = [(x+i*j,a,b,c,d,e,f,g,h,i,j) | (x,a,b,c,d,e,f,g,h)<-input , (i,j)<-garden , x+i*j < ar]

--consumehbktgv :: [(Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int)] -> Int -> [(Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int)]
consumehbktgv input ar = [(x+k*l,a,b,c,d,e,f,g,h,i,j,k,l) | (x,a,b,c,d,e,f,g,h,i,j)<-input , (k,l)<-balcony , x+k*l <= ar]  --possible ways of building halls bed kitchen toilet garden balcony


--combination :: Int -> Int -> Int -> [(Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int,Int)]
combination ar be ha = 
    do
        let maxk = ceiling (fromIntegral be/3)
        let possiblehb = consumehb ar be ha
        let possiblehbk = consumehbk possiblehb ar maxk
        let possiblehbkt = consumehbkt possiblehbk ar be
        let possiblehbktg = consumehbktg possiblehbkt ar
        let possiblehbktgv = consumehbktgv possiblehbktg ar
        let sorted = sortBy cmp possiblehbktgv
        return (head (reverse sorted))                                                                    --sorted possible combinations of configs 


design :: Int -> Int -> Int -> IO ()
design area bed hall = 
    do
        let list = combination area bed hall
        let (ar,a,b,c,d,e,f,g,h,i,j,k,l) = head list                                                  --head of sorted list contains the best possible config
        putStrLn ("Bedroom: " ++ (show bed) ++ " (" ++ (show a) ++ "x" ++ (show b) ++ ") Hall: " ++ (show hall) ++ " (" ++ (show c) ++ "x"++(show d)++") Kitchen: "++(show (ceiling (fromIntegral bed/3)))++" ("++(show e)++"x"++(show f)++") Bathroom: "++(show (bed+1))++" ("++(show g)++"x"++(show h)++") Garden: 1 ("++(show i)++"x"++(show j)++") Balcony: 1("++(show k)++"x"++(show l)++") Unused Space: "++(show (area-ar)))