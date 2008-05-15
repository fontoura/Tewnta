package br.ufrgs.f180.api;

import br.ufrgs.f180.math.Cartesian;

public interface Player {
	void login(Long id, Cartesian position);
}
