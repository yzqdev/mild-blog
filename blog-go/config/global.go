package config

import (
	"fmt"
	"github.com/gookit/color"
	"github.com/spf13/viper"
	"io/ioutil"
	"log"
	"os"
	"strings"
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

	conf := "./config.yml"
	viper.SetConfigFile(conf)
	content, err := ioutil.ReadFile(conf)
	if err != nil {
		log.Fatal(fmt.Sprintf("Read conf file fail: %s", err.Error()))
	}
	//Replace environment variables
	err = viper.ReadConfig(strings.NewReader(os.ExpandEnv(string(content))))
	if err != nil {
		log.Fatal(fmt.Sprintf("Parse conf file fail: %s", err.Error()))
	}

	if err = viper.Unmarshal(&g); err != nil {
		color.Red.Println("unable to decode into struct, %v", err)
	}

	return g
}
