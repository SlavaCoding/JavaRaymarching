package com.coursework.javaraymarching.renderer.shapes;

import com.coursework.javaraymarching.renderer.utils.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Box.class,           name = "Box"),
        @JsonSubTypes.Type(value = BoxFrame.class,      name = "BoxFrame"),
        @JsonSubTypes.Type(value = CappedTorus.class,   name = "CappedTorus"),
        @JsonSubTypes.Type(value = Capsule.class,       name = "Capsule"),
        @JsonSubTypes.Type(value = Cone.class,          name = "Cone"),
        @JsonSubTypes.Type(value = Cylinder.class,      name = "Cylinder"),
        @JsonSubTypes.Type(value = Floor.class,         name = "Floor"),
        @JsonSubTypes.Type(value = Sphere.class,        name = "Sphere"),
        @JsonSubTypes.Type(value = Torus.class,         name = "Torus")
})
public abstract class Shape {
    protected Vector3 center;
    private Material material;
    private Vector3 rotate;
    private Matrix3 rotateMatrix;
    private float smoothRadius;
    private float roundingRadius;
    private boolean isSmoothing;
    private boolean isRotated;
    private OperationMode mode;

    @JsonIgnore
    protected abstract float getDistance(Vector3 pos);

    public Shape(){
        this(new Vector3());
    }
    public Shape(Vector3 pos){
        center = pos;
        material = new Material();
        setRotate(new Vector3());
        smoothRadius = 0;
        isSmoothing = false;
        roundingRadius = 0;
        mode = OperationMode.Union;
    }

    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material){
        this.material = material;
    }

    public Vector3 getCenter() {
        return center;
    }
    public void setCenter(Vector3 center) {
        this.center = center;
    }

    public Vector3 getRotate() {
        return rotate;
    }
    public void setRotate(Vector3 rotate) {
        if(rotate.x!=0 || rotate.y!=0 || rotate.z!=0){
            isRotated = true;
            float cos = (float) Math.cos(Math.toRadians(rotate.x));
            float sin = (float) Math.sin(Math.toRadians(rotate.x));
            rotateMatrix = new Matrix3(1, 0, 0,
                                       0, cos, -sin,
                                       0, sin, cos);
            cos = (float) Math.cos(Math.toRadians(rotate.y));
            sin = (float) Math.sin(Math.toRadians(rotate.y));
            rotateMatrix.mul(new Matrix3(cos, 0, sin,
                                         0, 1, 0,
                                         -sin, 0, cos));
            cos = (float) Math.cos(Math.toRadians(rotate.z));
            sin = (float) Math.sin(Math.toRadians(rotate.z));
            rotateMatrix.mul(new Matrix3(cos, -sin, 0,
                                         sin, cos, 0,
                                         0, 0, 1));
        }
        else isRotated = false;
        this.rotate = rotate;
    }

    public float getSmoothRadius() {
        return smoothRadius;
    }
    public void setSmoothRadius(float smoothRadius) {
        isSmoothing = smoothRadius != 0;
        this.smoothRadius = smoothRadius;
    }

    public float getRoundingRadius() {
        return roundingRadius;
    }
    public void setRoundingRadius(float roundingRadius) {
        this.roundingRadius = roundingRadius;
    }

    public int getMode() {
        return mode.ordinal();
    }
    public void setMode(OperationMode mode) {
        this.mode = mode;
    }
    public void setMode(int mode) {
        this.mode = switch (mode){
            case 0 -> OperationMode.Union;
            case 1 -> OperationMode.Subtract;
            case 2 -> OperationMode.Intersect;
            default -> this.mode;
        };
    }
    public ShapeHit mappingShape(Vector3 pos, ShapeHit lastHit){
        float dist;
        Vector3 distToCenter = Vector3.sub(pos, center);
        if (isRotated){
            rotateMatrix.transformVector(distToCenter);
        }
        dist = getDistance(distToCenter) - roundingRadius;
        return switch (mode){
            case Union -> dist < lastHit.dist() ? new ShapeHit(dist, this) : lastHit;
            case Subtract -> -dist > lastHit.dist() ? new ShapeHit(-dist, this) : lastHit;
            case Intersect -> dist > lastHit.dist() ? new ShapeHit(dist, this) : lastHit;
        };

    }
    public RayHit mapping(Vector3 pos, RayHit lastHit){
        float dist;
        Vector3 distToCenter = Vector3.sub(pos, center);
        if (isRotated){
            rotateMatrix.transformVector(distToCenter);
        }
        dist = getDistance(distToCenter) - roundingRadius;
        if(isSmoothing){
            float blendDist, h;
            switch (mode) {
                case Union -> {
                    h = MathF.saturate(0.5f + 0.5f * (lastHit.dist() - dist) / smoothRadius);
                    blendDist = MathF.mix(lastHit.dist(), dist, h) - smoothRadius * h * (1 - h);
                }
                case Subtract -> {
                    h = MathF.saturate(0.5f - 0.5f * (lastHit.dist() + dist) / smoothRadius);
                    blendDist = MathF.mix(lastHit.dist(), -dist, h) + smoothRadius * h * (1 - h);
                }
                default -> {
                    h = MathF.saturate(0.5f - 0.5f * (lastHit.dist() - dist) / smoothRadius);
                    blendDist = MathF.mix(lastHit.dist(), dist, h) + smoothRadius * h * (1 - h);
                }
            }
            return new RayHit(blendDist, Material.blend(lastHit.m(), material, h));
        }
        else{
            return switch (mode){
                case Union -> dist < lastHit.dist() ? new RayHit(dist, material) : lastHit;
                case Subtract -> -dist > lastHit.dist() ? new RayHit(-dist, material) : lastHit;
                case Intersect -> dist > lastHit.dist() ? new RayHit(dist, material) : lastHit;
            };
        }
    }
}