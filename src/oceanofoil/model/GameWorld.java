/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oceanofoil.model;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import oceanofoil.model.property.MunicipalProperty;
import oceanofoil.model.property.PrivateProperty;
import oceanofoil.model.property.Property;
import oceanofoil.model.property.RailProperty;
import oceanofoil.model.property.upgrade.Barn;
import oceanofoil.model.property.upgrade.FarmHouse;
import oceanofoil.model.worldgenerator.PropertyGenerator;
import oceanofoil.model.worldgenerator.WorldGenerationConstants;
import oceanofoil.view.util.Point2f;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 *
 * @author Den
 */
public class GameWorld {

    private HashMap<Integer, Property> properties = new HashMap<Integer, Property>();

    private ArrayList<ArrayList<WorldBlock>> worldBlocks = new ArrayList<ArrayList<WorldBlock>>();
    private ArrayList<ArrayList<RailroadBlock>> railroadBlocks = new ArrayList<ArrayList<RailroadBlock>>();

    private HashMap<Property, Point2f> propertyTitleLocations = new HashMap<Property, Point2f>();

    public GameWorld(String worldXML) throws Exception {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        InputSource inSource = new InputSource();
        inSource.setCharacterStream(new StringReader(worldXML));

        Document worldDoc = builder.parse(inSource);

        Node propertiesNode = worldDoc.getElementsByTagName("properties").item(0);
        Node tilesNode = worldDoc.getElementsByTagName("tiles").item(0);

        NodeList propertiesList = propertiesNode.getChildNodes();

        for (int c = 0; c < propertiesList.getLength(); c++) {
            Node property = propertiesList.item(c);

            if (property.getNodeName().equals("property")) {
                NamedNodeMap attrs = property.getAttributes();

                int propId = Integer.parseInt(attrs.getNamedItem("id").getNodeValue());
                String type = attrs.getNamedItem("type").getNodeValue();
                int municipalId = Integer.parseInt(attrs.getNamedItem("municipalid").getNodeValue());

                if (type.equals("private")) {
                    properties.put(propId, new PrivateProperty(propId, municipalId, PropertyGenerator.getNextFamily()));
                } else if (type.equals("municipal")) {
                    properties.put(propId, new MunicipalProperty(propId, null));
                } else if (type.equals("railroad")) {
                    properties.put(propId, new RailProperty(propId, municipalId, null));
                } else {
                }
            }
        }

        NodeList tileRowList = tilesNode.getChildNodes();

        for (int c = 0; c < tileRowList.getLength(); c++) {
            Node tileRow = tileRowList.item(c);

            if (tileRow.getNodeName().equals("tilerow")) {

                ArrayList<WorldBlock> propertyTileRow = new ArrayList<WorldBlock>();
                ArrayList<RailroadBlock> rrTileRow = new ArrayList<RailroadBlock>();

                NodeList tileList = tileRow.getChildNodes();

                for (int t = 0; t < tileList.getLength(); t++) {
                    Node tile = tileList.item(t);

                    if (tile.getNodeName().equals("tile")) {
                        NamedNodeMap tileAttrs = tile.getAttributes();

                        int propId = Integer.parseInt(tileAttrs.getNamedItem("propertyId").getNodeValue());

                        WorldBlock block = properties.get(propId).createWorldBlock();

                        propertyTileRow.add(block);

                        boolean railroad = false;

                        if (tile.hasChildNodes()) {
                            NodeList upgradesList = tile.getChildNodes();

                            for (int u = 0; u < upgradesList.getLength(); u++) {
                                Node upgrade = upgradesList.item(u);

                                if (upgrade.getNodeName().equals("upgrade")) {
                                    NamedNodeMap upgradeAttrs = upgrade.getAttributes();

                                    String type = upgradeAttrs.getNamedItem("type").getNodeValue();

                                    if (type.equals("railroad")) {
                                        int rrId = Integer.parseInt(upgradeAttrs.getNamedItem("rrid").getNodeValue());
                                        RailProperty rrP = (RailProperty)properties.get(rrId);
                                        rrTileRow.add(rrP.createRailroadBlock());
                                        railroad = true;
                                    } else if (type.equals("propertyname")) {
                                        propertyTitleLocations.put(properties.get(propId), new Point2f(t, c));
                                    } else if (type.equals("barn")) {
                                        block.setTileUpgrade(new Barn(properties.get(propId)));
                                    } else if (type.equals("house")) {
                                        block.setTileUpgrade(new FarmHouse(properties.get(propId)));
                                    }
                                }
                            }

                        }
                        if (!railroad) {
                            rrTileRow.add(new RailroadBlock(null));
                        }
                    }
                }

                worldBlocks.add(propertyTileRow);
                railroadBlocks.add(rrTileRow);
            }
        }

        for (int r = 0; r < worldBlocks.size(); r++) {
            for (int c = 0; c < worldBlocks.get(r).size(); c++) {
                
                Property p = worldBlocks.get(r).get(c).getProperty();

                if (r > 0) {
                    if (worldBlocks.get(r - 1).get(c).getProperty()
                            != worldBlocks.get(r).get(c).getProperty()) {
                        p.registerNeighbor(worldBlocks.get(r - 1).get(c).getProperty());
                    }
                }

                if (r < worldBlocks.size() - 1) {
                    if (worldBlocks.get(r + 1).get(c).getProperty() != worldBlocks.get(r).get(c).getProperty()) {
                        p.registerNeighbor(worldBlocks.get(r + 1).get(c).getProperty());
                    }
                }

                if (c > 0) {
                    if (worldBlocks.get(r).get(c - 1).getProperty() != worldBlocks.get(r).get(c).getProperty()) {
                        p.registerNeighbor(worldBlocks.get(r).get(c - 1).getProperty());
                    }
                }

                if (c < worldBlocks.get(r).size() - 1) {
                    if (worldBlocks.get(r).get(c + 1).getProperty() != worldBlocks.get(r).get(c).getProperty()) {
                        p.registerNeighbor(worldBlocks.get(r).get(c + 1).getProperty());
                    }
                }

            }
        }

        int worldSize = worldBlocks.size() * worldBlocks.get(0).size();
        int mountainBlocks = (int)(worldSize * WorldGenerationConstants.PERCENT_ROCKY);

        ArrayList<WorldBlock> addedMountains = new ArrayList<WorldBlock>();

        for (int mountainCount = 0; mountainCount < mountainBlocks; mountainCount++) {
            int r = (int) (Math.random() * worldBlocks.size());
            int c = (int) (Math.random() * worldBlocks.get(0).size());

            worldBlocks.get(r).get(c).setTerrainType(WorldBlock.TerrainType.Rocky);

            generateTerrain(r, c, 0.2);
        }

    }

    private void generateTerrain(int r, int c, double mountainChance) {
        if (r > 0) {
            if (worldBlocks.get(r - 1).get(c).getTerrainType() == WorldBlock.TerrainType.Plain) {
                if (Math.random() > mountainChance) {
                    worldBlocks.get(r - 1).get(c).setTerrainType(WorldBlock.TerrainType.RollingHills);
                } else {
                    worldBlocks.get(r - 1).get(c).setTerrainType(WorldBlock.TerrainType.Rocky);
                    generateTerrain(r - 1, c, mountainChance / 2);
                }
            }
        }

        if (r < worldBlocks.size() - 1) {
            if (worldBlocks.get(r + 1).get(c).getTerrainType() == WorldBlock.TerrainType.Plain) {
                if (Math.random() > mountainChance) {
                    worldBlocks.get(r + 1).get(c).setTerrainType(WorldBlock.TerrainType.RollingHills);
                } else {
                    worldBlocks.get(r + 1).get(c).setTerrainType(WorldBlock.TerrainType.Rocky);
                    generateTerrain(r + 1, c, mountainChance / 2);
                }
            }
        }

        if (c > 0) {
            if (worldBlocks.get(r).get(c - 1).getTerrainType() == WorldBlock.TerrainType.Plain) {
                if (Math.random() > mountainChance) {
                    worldBlocks.get(r).get(c - 1).setTerrainType(WorldBlock.TerrainType.RollingHills);
                } else {
                    worldBlocks.get(r).get(c - 1).setTerrainType(WorldBlock.TerrainType.Rocky);
                    generateTerrain(r, c - 1, mountainChance / 2);
                }
            }
        }

        if (c < worldBlocks.get(r).size() - 1) {
            if (worldBlocks.get(r).get(c + 1).getTerrainType() == WorldBlock.TerrainType.Plain) {
                if (worldBlocks.get(r).get(c + 1).getTerrainType() == WorldBlock.TerrainType.Plain) {
                    if (Math.random() > mountainChance) {
                        worldBlocks.get(r).get(c + 1).setTerrainType(WorldBlock.TerrainType.RollingHills);
                    } else {
                        worldBlocks.get(r).get(c + 1).setTerrainType(WorldBlock.TerrainType.Rocky);
                        generateTerrain(r, c + 1, mountainChance / 2);
                    }
                }
            }
        }
    }

    public ArrayList<ArrayList<WorldBlock>> getWorldBlocks() {
        return worldBlocks;
    }

    public ArrayList<ArrayList<RailroadBlock>> getRailroadBlocks() {
        return railroadBlocks;
    }

    public ArrayList<Property> getProperties() {
        return new ArrayList<Property>(properties.values());
    }

    public HashMap<Property, Point2f> getPropertyTitleLocations() {
        return propertyTitleLocations;
    }
}
