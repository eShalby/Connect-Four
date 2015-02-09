import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Created by oj on 1/30/15.
 */
public class LinusAI implements Connect4AI {

    public ArrayList<Color[]> getGroups(Color[][] board) {
        ArrayList<Color[]> groupList = new ArrayList<Color[]>(69);
        //Down Groups: 0 - 20 inclusive
        //Column 0: 0 - 2
        //Column 1: 3 - 5
        //Column 2: 6 - 8
        int counter = -1;
        for (int col = 0; col < 7; col++) {
            for(int row = 0; row < 3; row++) {
                Color[] group = new Color[4];
                for (int j = 0; j < 4; j++) {
                    group[j] = board[row + j][col];
                    }
                    groupList.add(++counter, group);
                }
            }
        //Right Groups: 21 - 44 inclusive
        //Row 0: 21 - 24 -> 0 - 3
        //Row 1: 25 - 28 -> 4 - 7
        //Row 5: 41 - 44
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 4; col++) {
                Color[] group = new Color[4];
                for (int i = 0; i < 4; i++) {
                    group[i] = board[row][col + i];
                }
                    groupList.add(++counter, group);
            }
        }
        //Diagonal DownRight Groups: 45 - 56 inclusive
        //Row 0: 45 - 48 -> 0 - 3
        //Row 1: 49 - 52 -> 4 - 7
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {
                Color[] group = new Color[4];
                for (int k = 0; k < 4; k++) {
                    group[k] = board[row + k][col + k];
                }
                groupList.add(++counter, group);
            }
        }
        //Diagonal UpRight Groups: 57 - 68 inclusive
        //Row 5: 57 - 60
        //Row 4: 61 - 64
        for (int row = 5; row > 3 ; row--) {
            for (int col = 0; col < 4; col++) {
                Color[] group = new Color[4];
                for (int l = 0; l < 4; l++) {
                    group[l] = board[row-l][col+l];
                }
                groupList.add(++counter, group);
            }
        }
        return groupList;
        }

    public int getDangerous(Color[] group) {
        int counter = 0;
        for (int i = 0; i < group.length; i++) {
            if(group[i].equals(Color.RED))
                counter++;
            else if(group[i].equals(Color.YELLOW))
                counter--;
        }
        if(counter==3 || counter==-3)
            for (int i = 0; i < group.length; i++) {
                if(group[i].equals(Color.WHITE))
                    return i;
            }
        return -1;
    }

    public Color getCriticalColor(Color[] group) {
        if(group[0].equals(Color.WHITE))
            return group[1];
        else
            return group[0];
    }

    public int tilesBelow(Color[][] board, int row, int col) {
        int counter = 0;
        if (row >= 0 && col >= 0) {
            for (int i = row; i < 5; i++) {
                if (board[i + 1][col].equals(Color.WHITE))
                    counter++;
            }
            return counter;
        }
        return -1;
    }

    public boolean isPlayable(Color[][] board, int col) {
        return board[0][col].equals(Color.WHITE);
    }

    public int stuffToDo(Color[][] board, Color me) {
        //1000 = Win
        //100 = Lose
        //10 = Setting up our zugzwang
        //1 = Setting up their zugzwang
        //if stuffToDo >= 2, we do not have tempo
        //if stuffToDo >=10, we should first set up our zugzwang before theirs
        //if stuffToDo >=100, we should block the four
        //if stuffToDo >=1000, we should win
        ArrayList<Color[]> groups = getGroups(board);
        int stuff = 0;
        ArrayList<Color[]> myThreats = new ArrayList<Color[]>(69);
        ArrayList<Integer> groupIndexForMe = new ArrayList<Integer>(69);
        ArrayList<Color[]> oppThreats = new ArrayList<Color[]>(69);
        ArrayList<Integer> groupIndexForEw = new ArrayList<Integer>(69);
        for (int i = 0; i < groups.size(); i++) {
            if(getDangerous(groups.get(i))!=-1) {
                if(getCriticalColor(groups.get(i)).equals(me)) {
                    myThreats.add(myThreats.size(), groups.get(i));
                    groupIndexForMe.add(groupIndexForMe.size(), i);
                }
                else {
                    oppThreats.add(oppThreats.size(), groups.get(i));
                    groupIndexForEw.add(groupIndexForEw.size(), i);
                }
            }
        }
        // Controlling Zugzwang of myThreats, so that there is an odd number of tiles below all of my threats
        // (if there are an even number of spaces, play it, so that there is an odd number left)
        // Analyzed before controlling Zugzwang of oppThreats
        for (int i = 0; i < myThreats.size(); i++) {
            int groupIndex = groupIndexForMe.get(i);
            if (0 <= groupIndex && groupIndex <= 20) {
                if (getDangerous(myThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, groupIndex % 3 + getDangerous(myThreats.get(i)), groupIndex / 3);
                    if (zugzwangControl == 0 && isPlayable(board, groupIndex / 3))
                        stuff+=1000;
                    if (zugzwangControl % 2 == 0 && isPlayable(board, groupIndex / 3))
                        stuff+=10;
                }
            } else if (21 <= groupIndex && groupIndex <= 44) {

                if (getDangerous(myThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, (groupIndex - 21) / 4, (groupIndex - 21) % 4 + getDangerous(myThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 21) % 4 + getDangerous(myThreats.get(i))))
                        stuff+=1000;
                    if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 21) % 4 + getDangerous(myThreats.get(i))))
                        stuff+=10;
                }
            } else if (45 <= groupIndex && groupIndex <= 56) {
                if (getDangerous(myThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, (groupIndex - 45) / 4 + getDangerous(myThreats.get(i)), (groupIndex - 45) % 4 + getDangerous(myThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 45) % 4 + getDangerous(myThreats.get(i))))
                        stuff+=1000;
                    if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 45) % 4 + getDangerous(myThreats.get(i))))
                        stuff+=10;
                }
            } else if (57 <= groupIndex && groupIndex <= 68) {
                if (getDangerous(myThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, 5 - (groupIndex - 57) / 4 - getDangerous(myThreats.get(i)), (groupIndex - 57) % 4 + getDangerous(myThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 57) % 4 + getDangerous(myThreats.get(i))))
                        stuff+=1000;
                    if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 57) % 4 + getDangerous(myThreats.get(i))))
                        stuff+=10;
                }
            }
        }
        // Controlling Zugzwang of opponent's threats, so that there is an odd number of tiles below all of his threats
        // Similar to above, play if even
        // Done after myThreats
        for (int i = 0; i < oppThreats.size(); i++) {
            int groupIndex = groupIndexForEw.get(i);
            if (0 <= groupIndex && groupIndex <= 20) {
                if (getDangerous(oppThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, groupIndex % 3 + getDangerous(oppThreats.get(i)), groupIndex / 3);
                    if (zugzwangControl == 0 && isPlayable(board, groupIndex / 3))
                        stuff+=100;
                    if (zugzwangControl % 2 == 0)
                        stuff+=1;
                }
            } else if (21 <= groupIndex && groupIndex <= 44) {
                if (getDangerous(oppThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, (groupIndex - 21) / 4, (groupIndex - 21) % 4 + getDangerous(oppThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 21) % 4 + getDangerous(oppThreats.get(i))))
                        stuff+=100;
                    if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 21) % 4 + getDangerous(oppThreats.get(i))))
                        stuff+=1;
                }
            } else if (45 <= groupIndex && groupIndex <= 56) {
                if (getDangerous(oppThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, (groupIndex - 45) / 4 + getDangerous(oppThreats.get(i)), (groupIndex - 45) % 4 + getDangerous(oppThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 45) % 4 + getDangerous(oppThreats.get(i))))
                        stuff+=100;
                    if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 45) % 4 + getDangerous(oppThreats.get(i))))
                        stuff+=1;
                }
            } else if (57 <= groupIndex && groupIndex <= 68) {
                if (getDangerous(oppThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, 5 - (groupIndex - 57) / 4 - getDangerous(oppThreats.get(i)), (groupIndex - 57) % 4 + getDangerous(oppThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 57) % 4 + getDangerous(oppThreats.get(i))))
                        stuff+=100;
                    if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 57) % 4 + getDangerous(oppThreats.get(i))))
                        stuff+=1;
                }
            }
        }
        System.out.println("stuffToDo. myThreats.size() = " + myThreats.size() + ", oppThreats.size() = " + oppThreats.size() + ", stuff = " + stuff);
        return stuff;
    }

    public int analyzeBoard(Color[][] board, Color me) {
        //Add 1000 if immediate win is possible
        //Add 100 if immediate loss is possible
        //Add 10 if control of our zugzwang is possible
        ArrayList<Color[]> groups = getGroups(board);
        int preferableMove = -1;
        ArrayList<Color[]> myThreats = new ArrayList<Color[]>();
        ArrayList<Integer> groupIndexForMe = new ArrayList<Integer>();
        ArrayList<Color[]> oppThreats = new ArrayList<Color[]>();
        ArrayList<Integer> groupIndexForEw = new ArrayList<Integer>();
        for (int i = 0; i < groups.size(); i++) {
            if(getDangerous(groups.get(i))!=-1) {
                if(getCriticalColor(groups.get(i)).equals(me)) {
                    myThreats.add(myThreats.size(), groups.get(i));
                    groupIndexForMe.add(groupIndexForMe.size(), i);
                }
                else {
                    oppThreats.add(oppThreats.size(), groups.get(i));
                    groupIndexForEw.add(groupIndexForEw.size(), i);
                }
            }
        }
        // Controlling Zugzwang of myThreats, so that there is an odd number of tiles below all of my threats
        // (if there are an even number of spaces, play it, so that there is an odd number left)
        // Analyzed before controlling Zugzwang of oppThreats
        for (int i = 0; i < myThreats.size(); i++) {
            int groupIndex = groupIndexForMe.get(i);
            if (0 <= groupIndex && groupIndex <= 20) {
                if (getDangerous(myThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, groupIndex % 3 + getDangerous(myThreats.get(i)), groupIndex / 3);
                    if (zugzwangControl == 0 && isPlayable(board, groupIndex / 3))
                        if (groupIndex / 3 + 1000 > preferableMove)
                            preferableMove = groupIndex / 3 + 1000;
                    else if (zugzwangControl % 2 == 0 && isPlayable(board, groupIndex / 3))
                        if(groupIndex / 3 + 10 > preferableMove)
                            preferableMove = groupIndex / 3 + 10;
                }
            } else if (21 <= groupIndex && groupIndex <= 44) {
                if (getDangerous(myThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, (groupIndex - 21) / 4, (groupIndex - 21) % 4 + getDangerous(myThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 21) % 4 + getDangerous(myThreats.get(i))))
                        if ((groupIndex - 21) % 4 + getDangerous(myThreats.get(i)) + 1000 > preferableMove)
                            preferableMove = (groupIndex - 21) % 4 + getDangerous(myThreats.get(i)) + 1000;
                    else if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 21) % 4 + getDangerous(myThreats.get(i))))
                        if((groupIndex - 21) % 4 + getDangerous(myThreats.get(i)) + 10 > preferableMove)
                            preferableMove = (groupIndex - 21) % 4 + getDangerous(myThreats.get(i)) + 10;
                }
            } else if (45 <= groupIndex && groupIndex <= 56) {
                if (getDangerous(myThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, (groupIndex - 45) / 4 + getDangerous(myThreats.get(i)), (groupIndex - 45) % 4 + getDangerous(myThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 45) % 4 + getDangerous(myThreats.get(i))))
                        if ((groupIndex - 45) % 4 + getDangerous(myThreats.get(i)) + 1000 > preferableMove)
                            preferableMove = (groupIndex - 45) % 4 + getDangerous(myThreats.get(i)) + 1000;
                    else if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 45) % 4 + getDangerous(myThreats.get(i))))
                        if((groupIndex - 45) % 4 + getDangerous(myThreats.get(i)) + 10 > preferableMove)
                            preferableMove = (groupIndex - 45) % 4 + getDangerous(myThreats.get(i)) + 10;
                }
            } else if (57 <= groupIndex && groupIndex <= 68) {
                if (getDangerous(myThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, 5 - (groupIndex - 57) / 4 + 3 - getDangerous(myThreats.get(i)), (groupIndex - 57) % 4 + getDangerous(myThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 57) % 4 + getDangerous(myThreats.get(i))))
                        if ((groupIndex - 57) % 4 + getDangerous(myThreats.get(i)) + 1000 > preferableMove)
                            preferableMove = (groupIndex - 57) % 4 + getDangerous(myThreats.get(i)) + 1000;
                    else if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 57) % 4 + getDangerous(myThreats.get(i))))
                        if((groupIndex - 57) % 4 + getDangerous(myThreats.get(i)) + 10 > preferableMove)
                            preferableMove = (groupIndex - 57) % 4 + getDangerous(myThreats.get(i)) + 10;
                }
            }
        }
        // Controlling Zugzwang of opponent's threats, so that there is an odd number of tiles below all of his threats
        // Similar to above, play if even
        // Done after myThreats
        for (int i = 0; i < oppThreats.size(); i++) {
            int groupIndex = groupIndexForEw.get(i);
            if (0 <= groupIndex && groupIndex <= 20) {
                if (getDangerous(oppThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, groupIndex % 3 + getDangerous(oppThreats.get(i)), groupIndex / 3);
                    if (zugzwangControl == 0 && isPlayable(board, groupIndex / 3))
                        if (groupIndex / 3 + 100 > preferableMove)
                            preferableMove = groupIndex / 3 + 100;
                    else if (zugzwangControl % 2 == 0 && isPlayable(board, groupIndex / 3))
                        if(groupIndex / 3 > preferableMove)
                            preferableMove = groupIndex / 3;
                }
            } else if (21 <= groupIndex && groupIndex <= 44) {
                if (getDangerous(oppThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, (groupIndex - 21) / 4, (groupIndex - 21) % 4 + getDangerous(oppThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 21) % 4 + getDangerous(oppThreats.get(i))))
                        if ((groupIndex - 21) % 4 + getDangerous(oppThreats.get(i)) + 100 > preferableMove)
                            preferableMove = (groupIndex - 21) % 4 + getDangerous(oppThreats.get(i)) + 100;
                    else if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 21) % 4 + getDangerous(oppThreats.get(i))))
                        if((groupIndex - 21) % 4 + getDangerous(oppThreats.get(i)) > preferableMove)
                            preferableMove = (groupIndex - 21) % 4 + getDangerous(oppThreats.get(i));
                }
            } else if (45 <= groupIndex && groupIndex <= 56) {
                if (getDangerous(oppThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, (groupIndex - 45) / 4 + getDangerous(oppThreats.get(i)), (groupIndex - 45) % 4 + getDangerous(oppThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 45) % 4 + getDangerous(oppThreats.get(i))))
                        if ((groupIndex - 45) % 4 + getDangerous(oppThreats.get(i)) + 100 > preferableMove)
                            preferableMove = (groupIndex - 45) % 4 + getDangerous(oppThreats.get(i)) + 100;
                    else if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 45) % 4 + getDangerous(oppThreats.get(i))))
                        if((groupIndex - 45) % 4 + getDangerous(oppThreats.get(i)) > preferableMove)
                            preferableMove = (groupIndex - 45) % 4 + getDangerous(oppThreats.get(i));
                }
            } else if (57 <= groupIndex && groupIndex <= 68) {
                if (getDangerous(oppThreats.get(i)) != -1) {
                    int zugzwangControl = tilesBelow(board, 5 - (groupIndex - 57) / 4 - getDangerous(oppThreats.get(i)), (groupIndex - 57) % 4 + getDangerous(oppThreats.get(i)));
                    if (zugzwangControl == 0 && isPlayable(board, (groupIndex - 57) % 4 + getDangerous(oppThreats.get(i))))
                        if ((groupIndex - 57) % 4 + getDangerous(oppThreats.get(i)) + 100 > preferableMove)
                            preferableMove = (groupIndex - 57) % 4 + getDangerous(oppThreats.get(i)) + 100;
                    else if (zugzwangControl % 2 == 0 && isPlayable(board, (groupIndex - 57) % 4 + getDangerous(oppThreats.get(i))))
                        if((groupIndex - 57) % 4 + getDangerous(oppThreats.get(i)) > preferableMove)
                            preferableMove = (groupIndex - 57) % 4 + getDangerous(oppThreats.get(i));
                }
            }
        }
        System.out.println("Analyzing Board. preferableMove = " + preferableMove);
        return preferableMove;
    }

    public int inspectAndAlyze(Color[][] board, Color me) {
        int inspectBoard = stuffToDo(board, me);
        if(inspectBoard >= 1000) {
            int ans = analyzeBoard(board, me);
            System.out.println("Computing inspectAndAlyze: " + ans);
            if(ans >= 1000)
                return ans % 1000;
        }
        else if(inspectBoard >= 100) {
            int ans = analyzeBoard(board, me);
            System.out.println("Computing inspectAndAlyze: " + ans);
            if(ans >= 100)
                return ans % 100;
        }
        else if(inspectBoard >= 10) {
            int ans = analyzeBoard(board, me);
            System.out.println("Computing inspectAndAlyze: " + ans);
            if(ans >=10)
                return ans % 10;
        }
        else if(inspectBoard >=1) {
            int ans = analyzeBoard(board, me);
            System.out.println("Computing inspectAndAlyze: " + ans);
            return ans;
        }
            return -1;
    }

    @Override
    public int getMove(Color[][] board, Color me) {
        Color ew = Color.WHITE;
        if(me.equals(Color.RED)) {
            ew.equals(Color.YELLOW);
        }
        else if(me.equals(Color.YELLOW)) {
            ew.equals(Color.RED);
        }

        if(inspectAndAlyze(board, me) > -1 && isPlayable(board, inspectAndAlyze(board, me))) {
            int hypMove = inspectAndAlyze(board, me);
            System.out.println("Returning inspectAndAlyze: " + hypMove);
            return hypMove;


        }


        int turnOneChecker = 0;
        for (int col = 0; col < 7; col++) {
            if(board[5][col].equals(Color.WHITE))
                turnOneChecker++;
            if(turnOneChecker > 5 && isPlayable(board, 3)) {
                System.out.println("Returning turnOne");
                return 3;
            }

        }

        ArrayList<String> prefCols = new ArrayList<String>();
        ArrayList<String> unprefCols = new ArrayList<String>();
        for (int i = 0; i < 7; i++) {
            Integer x = new Integer(i);
            if(isPlayable(board, i))
                prefCols.add(prefCols.size(), x.toString());
            if(tilesBelow(board, 0, i) > -1)
            board[0 + tilesBelow(board, 0, i)][i] = me;
            if((stuffToDo(board, me) / 100) % 10 > 0) {
                unprefCols.add(unprefCols.size(), x.toString());
            }
        }

        for (int i = 0; i < unprefCols.size(); i++) {
            String x = unprefCols.get(i).toString();
            for (int j = 0; j < prefCols.size(); j++) {
                prefCols.remove(x);
            }
        }
        ArrayList<Integer> freeCols = new ArrayList<Integer>();
        for (int i = 0; i < 7; i++) {
            if(isPlayable(board, i))
                freeCols.add(freeCols.size(), i);
        }

        if(prefCols.size() > 0) {
            int randomPlay = (int)(Math.random() * prefCols.size());
            System.out.println("prefCols.size() = " + prefCols.size() + ", unprefCols.size() = " + unprefCols.size() + ", Returning prefCols: " + prefCols.get(randomPlay));
            return Integer.parseInt(prefCols.get(randomPlay));
        }

        else if(freeCols.size() > 0) {
            int randomPlay = (int)(Math.random() * freeCols.size());
            System.out.println("Returning freeCols: " + freeCols.get(randomPlay));
            return freeCols.get(randomPlay);
        }
        return (int)(Math.random() * 7);
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public String getName() {
        return "BetterThanJonAI";
    }
}
