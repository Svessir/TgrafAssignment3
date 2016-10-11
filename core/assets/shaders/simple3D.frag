
#ifdef GL_ES
precision mediump float;
#endif

uniform vec4 u_lightColorDiffuse;
uniform vec4 u_lightColor2Diffuse;
uniform vec4 u_lightColor3Diffuse;
uniform vec4 u_lightColorSpecular;
uniform vec4 u_lightColor2Specular;
uniform vec4 u_lightColor3Specular;
uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform vec4 u_globalAmbient;

uniform float u_materialShininess;

varying vec4 v_normal;
varying vec4 v_s;
varying vec4 v_h;
varying vec4 v_s2;
varying vec4 v_h2;
varying vec4 v_s3;
varying vec4 v_h3;

void main()
{
    float lambert = max(0.0, dot(v_normal, v_s) / (length(v_normal) * length(v_s)));
    float phong = max(0.0, dot(v_normal, v_h) / (length(v_normal) * length(v_h)));
    vec4 diffuseColor = lambert * u_lightColorDiffuse * u_materialDiffuse;     // lambert * u_lightDiffuse * u_materialDiffuse
    vec4 specularColor = pow(phong, u_materialShininess) * u_lightColorSpecular * u_materialSpecular;   // u_lightSpecular1 *

    vec4 lightColor1 = diffuseColor + specularColor;

    lambert = max(0.0, dot(v_normal, v_s2) / (length(v_normal) * length(v_s2)));
    phong = max(0.0, dot(v_normal, v_h2) / (length(v_normal) * length(v_h2)));
    diffuseColor = lambert * u_lightColor2Diffuse * u_materialDiffuse;
    specularColor = pow(phong, u_materialShininess) * u_lightColor2Specular * u_materialSpecular;

    vec4 lightcolor2 = diffuseColor + specularColor;

    lambert = max(0.0, dot(v_normal, v_s3) / (length(v_normal) * length(v_s3)));
    phong = max(0.0, dot(v_normal, v_h3) / (length(v_normal) * length(v_h3)));
    diffuseColor = lambert * u_lightColor3Diffuse * u_materialDiffuse;
    specularColor = pow(phong, u_materialShininess) * u_lightColor3Specular * u_materialSpecular;

    vec4 lightcolor3 = diffuseColor + specularColor;

    gl_FragColor = u_globalAmbient + lightColor1 + lightcolor2 + lightcolor3;// + lightcolor2 + lightcolor3;
    //gl_FragColor = u_globalAmbient + u_materialDiffuse + lightColor1;

}