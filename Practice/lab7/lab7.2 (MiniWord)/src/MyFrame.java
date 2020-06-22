import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.universe.*;

class MyFrame {

    private BranchGroup createSceneGraph(String text) {
        // корень branch graph
        BranchGroup objRoot = new BranchGroup();

        Transform3D t3D = new Transform3D();
        // размещение объекта
        t3D.setTranslation(new Vector3f(0.0f, -0.0f, -3.0f));
        TransformGroup objMove = new TransformGroup(t3D);
        objRoot.addChild(objMove);

        //вращение
        TransformGroup objSpin = new TransformGroup();
        objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objMove.addChild(objSpin);

        Appearance textAppear = new Appearance();
        ColoringAttributes textColor = new ColoringAttributes();
        textColor.setColor(1.0f, 0.0f, 0.0f);
        textAppear.setColoringAttributes(textColor);
        textAppear.setMaterial(new Material());

        // текст
        Font3D font3D = new Font3D(new Font("Courier New", Font.PLAIN, 1),
                new FontExtrusion());
        Text3D textGeom = new Text3D(font3D, new String(text));
        textGeom.setAlignment(Text3D.ALIGN_CENTER);
        Shape3D textShape = new Shape3D();
        textShape.setGeometry(textGeom);
        textShape.setAppearance(textAppear);
        objSpin.addChild(textShape);

        //угол вращения
        Alpha rotationAlpha = new Alpha(-1, 10000);
        RotationInterpolator rotator = new RotationInterpolator(rotationAlpha, objSpin);

        BoundingSphere bounds = new BoundingSphere();
        rotator.setSchedulingBounds(bounds);
        objSpin.addChild(rotator);

        //свет для объема
        DirectionalLight lightD = new DirectionalLight();
        lightD.setInfluencingBounds(bounds);
        lightD.setDirection(new Vector3f(0.0f, 0.0f, -7.0f));
        lightD.setColor(new Color3f(1.0f, 0.0f, 1.0f));
        objMove.addChild(lightD);

        AmbientLight lightA = new AmbientLight();
        lightA.setInfluencingBounds(bounds);
        objMove.addChild(lightA);

        return objRoot;
    }

    MyFrame(String text) {
        JFrame jf = new JFrame("Welcome");
        jf.setSize(800, 600);

        jf.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent winEvent) {

            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        //холст для рендеринга зд объектов
        Canvas3D canvas3D = new Canvas3D(config);
        canvas3D.setStereoEnable(false);
        panel.add("Center", canvas3D);

        BranchGroup scene = createSceneGraph(text);

        //вершина графа сцены
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        //размещение сцены
        simpleU.getViewingPlatform().setNominalViewingTransform();
        simpleU.addBranchGraph(scene);

        jf.getContentPane().add(panel, BorderLayout.CENTER);

        jf.setVisible(true);
    }


}