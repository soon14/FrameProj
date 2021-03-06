package com.lh.frameproj;

import java.io.File;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDaoGenerator {

    public static final int VERSION = 1;
    public static final String GREEN_DAO_CODE_PATH = "../FrameProj/app/src/main/java";

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(VERSION, "com.xjgj.mall.db");

        addTest(schema);
        addDestination(schema);

        File f = new File(GREEN_DAO_CODE_PATH);
        if (!f.exists()) {
            f.mkdirs();
        }
        new DaoGenerator().generateAll(schema, f.getAbsolutePath());
    }

    /**
     * 
     * @param schema
     */
    private static void addTest(Schema schema) {
        Entity note = schema.addEntity("Test");
        note.addIdProperty().autoincrement();
        note.addStringProperty("test").notNull();
    }

    /**
     * 场内订单目的地表
     * @param schema
     */
    private static void addDestination(Schema schema) {
        Entity note = schema.addEntity("Destination");
        note.addIdProperty().autoincrement();
        note.addStringProperty("termini").notNull();
    }

}
