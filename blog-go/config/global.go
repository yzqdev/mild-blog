package config

import (
	"fmt"
	"log"
	"os"
	"strings"

	"github.com/spf13/viper"
)

type Global struct {
	Server struct {
		Port string `mapstructure:"port"`
	}
	Mysql struct {
		Host string `mapstructure:"host"`
		Port string `mapstructure:"port"`
		User string `mapstructure:"user"`
		Pass string `mapstructure:"pass"`
		Name string `mapstructure:"name"`
	}
	Pg struct {
		Host string `mapstructure:"host"`
		Port string `mapstructure:"port"`
		User string `mapstructure:"user"`
		Pass string `mapstructure:"pass"`
		Name string `mapstructure:"name"`
	}
}

var g *Global

func GetGlobal() *Global {
	if g != nil {
		return g
	}

	conf := "./config.yml"
	viper.SetConfigFile(conf)
	content, err := os.ReadFile(conf)
	if err != nil {
		log.Fatal(fmt.Sprintf("Read conf file fail: %s", err.Error()))
	}

	err = viper.ReadConfig(strings.NewReader(os.ExpandEnv(string(content))))
	if err != nil {
		log.Fatal(fmt.Sprintf("Parse conf file fail: %s", err.Error()))
	}

	if err = viper.Unmarshal(&g); err != nil {
		log.Fatal(fmt.Sprintf("Unable to decode into struct: %v", err))
	}

	return g
}
