package com.sourcegraph.javagraph;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.simple.JSONAware;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;

import com.sourcegraph.javagraph.DepresolveCommand.Resolution;
import com.sourcegraph.javagraph.SourceUnit.RawDependency;

public class Symbol implements JSONStreamAware, JSONAware {
	Symbol.Key key;
	String kind;
	String name;

	String file;
	int identStart;
	int identEnd;
	int defStart;
	int defEnd;

	List<String> modifiers;

	String pkg;

	String doc;

	String typeExpr;
	


	@Override
	public String toJSONString() {
		StringWriter b = new StringWriter();
		try {
			writeJSONString(b);
		} catch (IOException e) {
		}
		return b.toString();
	}

	@Override
	public void writeJSONString(Writer out) throws IOException {
		LinkedHashMap<String, Object> obj = new LinkedHashMap<>();
		if (key.origin != null)
			obj.put("origin", key.origin);
		obj.put("path", key.path);
		obj.put("kind", kind);
		obj.put("name", name);
		obj.put("file", file);
		obj.put("identStart", identStart);
		obj.put("identEnd", identEnd);
		obj.put("defStart", defStart);
		obj.put("defEnd", defEnd);
		obj.put("modifiers", modifiers);
		obj.put("pkg", pkg);
		if (doc != null && doc != "")
			obj.put("doc", doc);
		obj.put("typeExpr", typeExpr);
		JSONValue.writeJSONString(obj, out);
	}

	public static class Key {
		String origin;
		String path;

		public Key(String origin, String path) {
			this.origin = origin;
			this.path = path;
		}

		@Override
		public String toString() {
			return "SymbolKey{origin: " + origin + " path:" + path + "}";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((origin == null) ? 0 : origin.hashCode());
			result = prime * result + ((path == null) ? 0 : path.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Key other = (Key) obj;
			if (origin == null) {
				if (other.origin != null)
					return false;
			} else if (!origin.equals(other.origin))
				return false;
			if (path == null) {
				if (other.path != null)
					return false;
			} else if (!path.equals(other.path))
				return false;
			return true;
		}
		
		public String formatPath() {
			return path.replace('.', '/').replace('$', '.');
		}
		
		public String formatTreePath() {
			return formatPath();
		}
		
		private static String originURIJARFilePrefix = "jar:file:";
		
		public Resolution resolveOrigin(List<RawDependency> dependencies) {
			if(origin == "") return null;
			if(origin.split("!").length < 2) return null;
			
			String jarfile = origin.split("!")[0];
			
			if(jarfile.contains("jre/lib/")) return Resolution.StdLib();
			
			for(RawDependency dep : dependencies) {
				if(origin.startsWith(originURIJARFilePrefix + dep.JarPath)) {
					return dep.Resolve();
				}
			}
			
			return null;
		}
	}
	

}