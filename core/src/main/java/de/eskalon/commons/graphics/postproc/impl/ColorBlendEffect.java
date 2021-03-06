/*
 * Copyright 2020 eskalon
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 * http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.eskalon.commons.graphics.postproc.impl;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;

import de.eskalon.commons.graphics.postproc.ShaderPostProcessingEffect;
import de.eskalon.commons.utils.graphics.GL32CMacIssueHandler;

/**
 * A post processing effect that is blending the current scene with a color.
 * 
 * @author damios
 */
public class ColorBlendEffect extends ShaderPostProcessingEffect {

	// @formatter:off
	private static String getFragmentShader(Color color) {
		if(GL32CMacIssueHandler.doUse32CShader()) 
			return "#version 150\n" + 
					"#ifdef GL_ES\n" + 
					"    precision mediump float;\n" + 
					"#endif\n" + 
					"\n" + 
					"in vec4 v_color;\n" + 
					"in vec2 v_texCoords;\n" + 
					"\n" +
					"uniform sampler2D u_texture;\n" + 
					"uniform mat4 u_projTrans;\n" + 
					"\n" +
					"out vec4 fragColor;\n" + 
					"\n" + 
					"void main() {\n" +  
					"        fragColor = texture(u_texture, v_texCoords);\n" +
					"        fragColor.rgb = mix(fragColor.rgb, vec3("+color.r+","+color.b+","+color.g+"), "+color.a+");\n" + 
					"}";
		
		return "#version " + GL32CMacIssueHandler.
				getDefaultShaderVersion() + "\n" + 
				"#ifdef GL_ES\n" + 
				"    precision mediump float;\n" + 
				"#endif\n" + 
				"\n" + 
				"varying vec4 v_color;\n" + 
				"varying vec2 v_texCoords;\n" + 
				"\n" +
				"uniform sampler2D u_texture;\n" + 
				"uniform mat4 u_projTrans;\n" + 
				"\n" +
				"void main() {\n" +  
				"        gl_FragColor = texture2D(u_texture, v_texCoords);\n" +
				"        gl_FragColor.rgb = mix(gl_FragColor.rgb, vec3("+color.r+","+color.b+","+color.g+"), "+color.a+");\n" + 
				"}";
	}
	// @formatter:on

	public ColorBlendEffect(Camera camera, Color color) {
		super(camera, getDefaultVertexShader(), getFragmentShader(color));
	}

}
