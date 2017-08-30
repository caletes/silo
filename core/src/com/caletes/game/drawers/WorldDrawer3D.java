package com.caletes.game.drawers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.caletes.game.Camera3D;
import com.caletes.game.models.World;
import com.caletes.game.models.items.Item;
import com.caletes.game.models.items.cubes.Cube;
import com.caletes.game.octree.MortonCode;
import com.caletes.game.octree.Node;
import com.caletes.game.octree.NodeIterator;
import com.caletes.game.octree.OctreeOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;

public class WorldDrawer3D implements Drawer {

    public static final int SUBSTRACT_EXPONENT =5;

    private static World world;
    private static ModelBatch batch;
    private static Camera3D camera;


    private Model model;
    private Environment environment;
    private ArrayList<ModelInstance> instances = new ArrayList<>();

    public WorldDrawer3D(World world, ModelBatch batch, Camera3D camera) {
        this.world = world;
        this.batch = batch;
        this.camera = camera;


        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);


        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));


    }

    public void build() {
        try {
            int cameraX = 127;
            int cameraY = 127;
            int cameraZ = 1;


            if (world.isWithinBounds(cameraX, cameraY, cameraZ)) {
                Node sub = world.substract(cameraX, cameraY, cameraZ, getSubstractExponent());
                System.out.println(sub.getSize());
                List<Node> nodes = sub.withNeighbors();
                for (Node octree : nodes) {
                    NodeIterator it = octree.iterator();
                    while (it.hasNext()) {
                        Node<Cube> node = it.next();
                        if (node != null) {
                            Item item = node.getObject();
                            if (item != null && item.isVisible()) {
                                MortonCode.Vector3 position = node.getPosition();
                                instances.add(new ModelInstance(model, position.x * 5, position.y * 5, position.z * 5));
                            }
                        }
                    }
                }
            }
        } catch (OctreeOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    public void draw() {
        batch.begin(camera);
        Vector3 tempVector = new Vector3();
        for (ModelInstance instance : instances) {
            instance.transform.getTranslation(tempVector);
            boolean b = camera.frustum.sphereInFrustum(tempVector,16);
            if (b){
                batch.render(instance, environment);
            }
        }
        batch.end();
    }

    private int getSubstractExponent() {
        return Math.min(SUBSTRACT_EXPONENT, world.getExponent());
    }

    public void dispose() {
       batch.dispose();
       model.dispose();
    }
}
