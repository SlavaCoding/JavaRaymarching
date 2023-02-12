package com.coursework.javaraymarching.renderer.utils;

public class Matrix3 {
    private float[][] m;

    public Matrix3(){
        m = new float[3][3];
    }

    public Matrix3(Vector3 a, Vector3 b, Vector3 c){
        m = new float[3][3];
        m[0][0] = a.x; m[0][1] = a.y; m[0][2] = a.z;
        m[1][0] = b.x; m[1][1] = b.y; m[1][2] = b.z;
        m[2][0] = c.x; m[2][1] = c.y; m[2][2] = c.z;
    }
    public Matrix3(float ax, float ay,  float az,
                   float bx, float by,  float bz,
                   float cx, float cy,  float cz){
        m = new float[3][3];
        m[0][0] = ax; m[0][1] = ay; m[0][2] = az;
        m[1][0] = bx; m[1][1] = by; m[1][2] = bz;
        m[2][0] = cx; m[2][1] = cy; m[2][2] = cz;
    }

    public void mul(Matrix3 n){
        float[][] res = new float[3][3];
        for(int i=0; i<3; ++i){
            for(int j=0; j<3; ++j){
                for(int k=0; k<3; ++k){
                    res[i][j] += m[i][k] * n.m[k][j];
                }
            }
        }
        m = res;
    }

    public void transformVector(Vector3 v){
        float x = v.x; float y = v.y;
        v.x = x*m[0][0] + y*m[1][0] + v.z*m[2][0];
        v.y = x*m[0][1] + y*m[1][1] + v.z*m[2][1];
        v.z = x*m[0][2] + y*m[1][2] + v.z*m[2][2];
    }
}