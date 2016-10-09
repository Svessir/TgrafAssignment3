
#ifdef GL_ES
precision mediump float;
#endif

uniform vec4 u_lightColor;
uniform vec4 u_materialDiffuse;
uniform vec4 u_materialSpecular;
uniform vec4 u_globalAmbient;

uniform float u_materialShininess;

varying vec4 v_normal;
varying vec4 v_s;
varying vec4 v_h;

void main()
{
    float lambert = max(0.0, dot(v_normal, v_s) / (length(v_normal) * length(v_s)));
    float phong = max(0.0, dot(v_normal, v_h) / (length(v_normal) * length(v_h)));
    vec4 diffuseColor = lambert * u_lightColor * u_materialDiffuse;
    vec4 specularColor = pow(phong, u_materialShininess) * u_lightColor * u_materialSpecular;

    vec4 lightColor1 = diffuseColor + specularColor;

    gl_FragColor = u_globalAmbient + lightColor1;
    //gl_FragColor = u_globalAmbient + u_materialDiffuse + lightColor1;

}