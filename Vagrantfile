
Vagrant.configure("2") do |config|
  config.vm.box = "bento/ubuntu-16.04"
  config.vm.network "forwarded_port", guest: 8080, host: 28080
  config.vm.network "forwarded_port", guest: 9990, host: 29990

  config.vm.provider :virtualbox do |vb|
    vb.name = "VM-Monolith"
    vb.memory = 3000
    vb.cpus = 2
  end

  config.vm.provision :shell, path: "bootstrap.sh"
  config.vm.provision :shell, run: "always", path: "deploy.sh"
 end
