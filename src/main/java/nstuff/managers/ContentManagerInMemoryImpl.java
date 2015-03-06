package nstuff.managers;

import com.google.inject.Module;
import nstuff.economy.entity.building.BuildingBlueprint;
import nstuff.economy.entity.building.moudles.FunctionalModule;
import nstuff.economy.entity.building.moudles.MineModule;
import nstuff.economy.entity.building.moudles.ProductionModule;
import nstuff.economy.entity.resources.RecipeBlueprint;
import nstuff.economy.entity.resources.ResourceBlueprint;
import nstuff.economy.entity.resources.ResourceWithAmount;
import nstuff.economy.entity.resources.ResourceWithAmountBlueprint;
import nstuff.economy.entity.town.TownHall;
import nstuff.economy.entity.town.TownHallBlueprint;
import nstuff.world.logic.generators.entity.ProtoBiomeBlueprint;
import nstuff.world.settings.SettingException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Created by Ivan.Ochincenko on 06.03.15.
 */
public class ContentManagerInMemoryImpl implements ContentManager {

    private static final String RESOURCES_NAME = "resources.xml";

    private static final String BUILDINGS_NAME = "buildings.xml";

    private static final String TOWN_HALLS_NAME = "townhalls.xml";

    private static final String RECIPE_NAME = "recipes.xml";

    private Dictionary<String,ResourceBlueprint> allResource = new Hashtable<String, ResourceBlueprint>();

    private Dictionary<String,BuildingBlueprint> allBuildings = new Hashtable<String, BuildingBlueprint>();

    private Dictionary<String,TownHallBlueprint> allTownHalls = new Hashtable<String, TownHallBlueprint>();

    private Dictionary<String,RecipeBlueprint> allRecipe = new Hashtable<String, RecipeBlueprint>();

    @Override
    public void init() throws SettingException {
        List<ResourceBlueprint> resources= getList(RESOURCES_NAME,ResourceBlueprint.class);
        for(ResourceBlueprint resourceBlueprint : resources){
            allResource.put(resourceBlueprint.getName(),resourceBlueprint);
        }

        List<RecipeBlueprint> recipes= getList(RECIPE_NAME,RecipeBlueprint.class);
        for(RecipeBlueprint recipeBlueprint : recipes){
            allRecipe.put(recipeBlueprint.getName(),recipeBlueprint);


            recipeBlueprint.setNeededList(getResourceWithAmounts(recipeBlueprint.getNeededListBlueprint()));


            recipeBlueprint.setResultList(getResourceWithAmounts(recipeBlueprint.getResultListBlueprint()));
        }



        List<BuildingBlueprint> buildings= getList(BUILDINGS_NAME,BuildingBlueprint.class);
        for(BuildingBlueprint buildingBlueprint : buildings){
            allBuildings.put(buildingBlueprint.getName(),buildingBlueprint);


            buildingBlueprint.setNeededList( getResourceWithAmounts(buildingBlueprint.getNeededListBlueprint()));

            buildingBlueprint.setRecipeBlueprints(getRecipes(buildingBlueprint.getRecipeNames()));
            for(FunctionalModule functionalModule : buildingBlueprint.getModules()){
                switch(functionalModule.getType()){
                    case MINE:{
                        MineModule module = (MineModule)functionalModule;
                        module.setMineResources(getResourceWithAmounts(module.getMineResourcesBlueprint()));
                    }
                    break;
                    case PRODUCTION:{
                        ProductionModule module = (ProductionModule)functionalModule;
                        module.setRecipeBlueprints(getRecipes(module.getRecipeNames()));
                    }
                    break;
                }
            }

        }

        List<TownHallBlueprint> townHalls= getList(TOWN_HALLS_NAME,TownHallBlueprint.class);

        for(TownHallBlueprint townHallBlueprint : townHalls){
            allTownHalls.put(townHallBlueprint.getName(),townHallBlueprint);
            townHallBlueprint.setNeededList(getResourceWithAmounts(townHallBlueprint.getNeededListBlueprint()));
            townHallBlueprint.setAllowedBuildings(getBuildings(townHallBlueprint.getAllowedBuildingsNames()));


        }
        for(TownHallBlueprint townHallBlueprint : townHalls){
            List<TownHallBlueprint> list = new ArrayList<TownHallBlueprint>();
            for(String name : townHallBlueprint.getNextTownHallsNames()){
                list.add(allTownHalls.get(name));
            }
            townHallBlueprint.setNextTownHalls(list);
        }
    }

    private List<BuildingBlueprint> getBuildings(List<String> buildingName) {
        List<BuildingBlueprint> answer =new ArrayList<BuildingBlueprint>();
        for(String name : buildingName){
            answer.add(allBuildings.get(name));
        }
        return  answer;
    }

    private List<RecipeBlueprint> getRecipes(List<String> recipeNames) {
        List<RecipeBlueprint> answer =new ArrayList<RecipeBlueprint>();
        for(String name : recipeNames){
            answer.add(allRecipe.get(name));
        }
        return  answer;
    }

    private List<ResourceWithAmount> getResourceWithAmounts(List<ResourceWithAmountBlueprint> blueprintList) {
        List<ResourceWithAmount> resourcesForBuilding = new ArrayList<ResourceWithAmount>();
        for(ResourceWithAmountBlueprint resourceWithAmountBlueprint : blueprintList){
            resourcesForBuilding.add(new ResourceWithAmount(allResource.get(resourceWithAmountBlueprint.getResource()),resourceWithAmountBlueprint.getAmount()));
        }
        return resourcesForBuilding;
    }

    public <T> List<T> getList(String fileName,Class<T> tClass) throws SettingException {
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
            if(url==null){
                throw new SettingException("No "+ tClass.toString()+" file");
            }
            String path = url.getFile();
            if(path==null){
                throw new SettingException("No "+ tClass.toString()+" file");
            }



            File fXmlFile = new File(url.getFile());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            String elementNames  =  tClass.getAnnotation(XmlRootElement.class).name();
            NodeList nodeList =doc.getElementsByTagName(elementNames);
            List<T> answer = new LinkedList<T>();
            for(int i =0;i<nodeList.getLength();i++){
                JAXBContext jc = JAXBContext.newInstance(tClass);
                Unmarshaller u = jc.createUnmarshaller();
                Object obj = u.unmarshal(nodeList.item(i));
                T blueprint = tClass.cast(obj);
                answer.add(blueprint);
            }
            return answer;

        } catch (JAXBException e) {
            throw new SettingException(e);
        } catch (ParserConfigurationException e) {
            throw new SettingException(e);
        } catch (SAXException e) {
            throw new SettingException(e);
        } catch (IOException e) {
            throw new SettingException(e);
        }
    }

    @Override
    public ResourceBlueprint getResource(String name) {
        return allResource.get(name);
    }

    @Override
    public BuildingBlueprint getBuilding(String name) {
        return allBuildings.get(name);
    }

    @Override
    public TownHallBlueprint getTownHall(String name) {
        return allTownHalls.get(name);
    }

    @Override
    public RecipeBlueprint getRecipe(String name) {
        return allRecipe.get(name);
    }

    @Override
    public List<TownHallBlueprint> getTownHallByCriteria(TownHallCriteria criteria) {
        List<TownHallBlueprint> halls =new ArrayList<TownHallBlueprint>();
        Enumeration<TownHallBlueprint> enumeration = allTownHalls.elements();
        while(enumeration.hasMoreElements()){
            TownHallBlueprint townHall = enumeration.nextElement();
            if(criteria.suitable(townHall)){
                halls.add(townHall);
            }

        }
        return halls;
    }


}
