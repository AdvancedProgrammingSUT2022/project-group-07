import game.Common.Enum.TypeOfTechnology;
import game.Common.Enum.regexes.GameMenuCommands;
import game.Common.Model.Civilization;
import game.Common.Model.Technology;
import game.Common.Model.User;
import game.Server.Controller.game.ResearchController;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ResearchTest {

    @Mock
    Civilization civilization ;

    public static Technology technology ;
    public static ArrayList<Technology> technologies ;

    @BeforeAll
    static void beforeAll(){
        technology = new Technology(TypeOfTechnology.AGRICULTURE);
        technologies = new ArrayList<>();
        technologies.add(technology);
    }

    @AfterAll
    static void afterAll(){
        technologies = null;
    }

    @Test
    public void checkTurn(){
        technology.setRemainingTurns(technology.getRemainingTurns()-1);
        int result = technology.getRemainingTurns();
        assertEquals(TypeOfTechnology.AGRICULTURE.getTurnsNeeded()-1 , result);
    }

    @Test
    public void checkCurrentResearch(){
        when(civilization.getCurrentResearch()).thenReturn(null);
        String result = ResearchController.showCurrentResearch(civilization) ;
        assertEquals("no research" , result);
    }

    @Test
    public void checkCurrentResearchNotNull(){
        when(civilization.getCurrentResearch()).thenReturn(technology) ;
        String result = ResearchController.showCurrentResearch(civilization) ;
        String expected = "technology : "+ technology.getTypeOfTechnology().getName()
                + "\nremaining turns : " + technology.getRemainingTurns() + "\n" ;
        assertEquals(expected , result);
    }

    @Test
    public void checkOwnedResearchNull(){
        when(civilization.getGainedTechnologies()).thenReturn(new ArrayList<Technology>());
        String result = ResearchController.showOwnedResearch(civilization);
        String expected = "no technology" ;
        assertEquals(expected , result);
    }

    @Test
    public void checkOwnedResearchNotNull(){
        when(civilization.getGainedTechnologies()).thenReturn(technologies);
        String result = ResearchController.showOwnedResearch(civilization) ;
        String expected = "list of owned technologies : \n"
                + technology.getTypeOfTechnology().getName()
                + "\n" + "total number : " + technologies.size() + "\n";
        assertEquals(expected , result);
    }

    @Test
    public void checkAvailableResearchEasy(){
        when(civilization.getGainedTechnologies()).thenReturn(technologies);
        String result = ResearchController.showAvailableResearch(civilization) ;
        String expected = """
                list of available researches :\s
                Animal Husbandry
                Mining
                Pottery
                Archery
                """;
        ;
        assertEquals(expected , result);
    }

    @Test
    public void checkAvailableResearchNotEasy(){
        technologies.add(new Technology(TypeOfTechnology.MINING));
        when(civilization.getGainedTechnologies()).thenReturn(technologies);
        String result = ResearchController.showAvailableResearch(civilization) ;
        String expected = """
                list of available researches :\s
                Animal Husbandry
                Pottery
                Archery
                Bronze Working
                Masonry
                """;
        ;
        assertEquals(expected , result);
    }

    @Test
    public void checkIsScienceEnough(){
        when(civilization.getScience()).thenReturn(0);
        Matcher matcher = null;
        String result = ResearchController.researchTechnology(matcher , civilization);
        String expected = "you don't have any science";
        assertEquals(expected , result);
    }

    @Test
    public void checkInvalidResearchName(){
        when(civilization.getScience()).thenReturn(20);
        String input = "research technology Programming" ;
        Matcher matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_TECHNOLOGY) ;
        String result = ResearchController.researchTechnology(matcher , civilization);
        String expected = "no technology with name Programming" ;
        assertEquals(expected , result);
    }

    @Test
    public void checkValidOwnedResearch(){
        when(civilization.getScience()).thenReturn(20) ;
        when(civilization.getGainedTechnologies()).thenReturn(technologies);
        String input = "research technology " + TypeOfTechnology.AGRICULTURE.getName();
        Matcher matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_TECHNOLOGY) ;
        String result = ResearchController.researchTechnology(matcher , civilization);
        String expected = "you already achieved this technology" ;
        assertEquals(expected , result);
    }

    @Test
    public void checkValidUnavailableResearch(){
        when(civilization.getScience()).thenReturn(20) ;
        when(civilization.getGainedTechnologies()).thenReturn(technologies);
        String input = "research technology " + TypeOfTechnology.RADIO.getName();
        Matcher matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_TECHNOLOGY) ;
        String result = ResearchController.researchTechnology(matcher , civilization);
        String expected = "you don't have all prerequisite technologies" ;
        assertEquals(expected , result);
    }

    @Test
    public void checkValidAvailableResearch(){
        when(civilization.getScience()).thenReturn(20) ;
        when(civilization.getGainedTechnologies()).thenReturn(technologies);
        String input = "research technology " + TypeOfTechnology.MINING.getName();
        Matcher matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_TECHNOLOGY) ;
        String result = ResearchController.researchTechnology(matcher , civilization);
        String expected = "research " + TypeOfTechnology.MINING.getName() + " set successfully" ;
        assertEquals(expected , result);
    }

    @Test
    public void checkValidAlreadyInProgressResearch(){
        when(civilization.getScience()).thenReturn(20) ;
        when(civilization.getGainedTechnologies()).thenReturn(technologies);
        when(civilization.getCurrentResearch()).thenReturn(new Technology(TypeOfTechnology.RADIO));
        String input = "research technology " + TypeOfTechnology.RADIO.getName();
        Matcher matcher = GameMenuCommands.getMatcher(input , GameMenuCommands.RESEARCH_TECHNOLOGY) ;
        String result = ResearchController.researchTechnology(matcher , civilization);
        String expected = "you are already researching for this technology" ;
        assertEquals(expected , result);
    }

    @Test
    public void checkScienceAfterSuccessfulResearch(){
        Civilization civilization = new Civilization("persia sefid" , new User("ap" , "ap" , "ap")) ;
        civilization.setScience(100);
        civilization.setCurrentResearch(technology);
        int result = civilization.getScience() ;
        int expected = 100 - technology.getTypeOfTechnology().getCost() ;
        assertEquals(expected , result);
    }

}
