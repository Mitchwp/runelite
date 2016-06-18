/*
 * Copyright (c) 2016, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. All advertising materials mentioning features or use of this software
 *    must display the following acknowledgement:
 *    This product includes software developed by Adam <Adam@sigterm.info>
 * 4. Neither the name of the Adam <Adam@sigterm.info> nor the
 *    names of its contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Adam <Adam@sigterm.info> ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Adam <Adam@sigterm.info> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.runelite.asm.attributes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.runelite.asm.attributes.annotation.Annotation;
import net.runelite.asm.signature.Type;

public class Annotations extends Attribute
{
	private final List<Annotation> annotations = new ArrayList<>();
	
	public Annotations(Attributes attributes)
	{
		super(attributes, AttributeType.RUNTIMEVISIBLEANNOTATIONS);
	}

	public List<Annotation> getAnnotations()
	{
		return annotations;
	}
	
	public void addAnnotation(Annotation annotation)
	{
		annotations.add(annotation);
	}

	public void removeAnnotation(Annotation annotation)
	{
		annotations.remove(annotation);
	}

	public void clearAnnotations()
	{
		annotations.clear();
	}
	
	public Annotation find(Type type)
	{
		for (Annotation a : annotations)
			if (a.getType().equals(type))
				return a;
		return null;
	}
	
	@Override
	public void loadAttribute(DataInputStream is) throws IOException
	{
		int num_annotations = is.readUnsignedShort();
		for (int i = 0; i < num_annotations; ++i)
		{
			Annotation a = new Annotation(this);
			a.load(is);
			annotations.add(a);
		}
	}

	@Override
	public void writeAttr(DataOutputStream out) throws IOException
	{
		out.writeShort(annotations.size());
		for (Annotation a : annotations)
		{
			a.write(out);
		}
	}

}
